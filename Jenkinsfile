pipeline {
    agent any

    environment {
        IMAGE_NAME = 'oneyear-backend'
        SONARQUBE_SERVER = 'MySonarQube'
    }

    triggers {
        githubPush()
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Free up memory for the build') {
            steps {
                sh 'docker stop oneyear-backend || true'
            }
        }

        stage('Build & Unit Test') {
            steps {
                sh 'chmod +x ./mvnw'
                sh './mvnw clean verify -DskipTests'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_SERVER}") {
                    sh './mvnw sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    retry(3) {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} -t ${IMAGE_NAME}:latest ."
            }
        }

        stage('Deploy') {
            steps {
                // The database is Neon (external), so there is only one
                // container to run - no docker compose needed. Credentials
                // come from Jenkins, not from a .env file on the host.
                withCredentials([
                    string(credentialsId: 'neon-db-url', variable: 'DB_URL'),
                    usernamePassword(credentialsId: 'neon-db-creds',
                                     usernameVariable: 'DB_USER',
                                     passwordVariable: 'DB_PASS')
                ]) {
                    // Single-quoted so Groovy does not interpolate the secrets;
                    // the shell expands them instead and Jenkins masks them in logs.
                    sh '''
                        docker rm -f oneyear-backend || true
                        docker run -d --name oneyear-backend \
                          --restart unless-stopped \
                          -p 8092:8092 \
                          -e SPRING_DATASOURCE_URL="$DB_URL" \
                          -e SPRING_DATASOURCE_USERNAME="$DB_USER" \
                          -e SPRING_DATASOURCE_PASSWORD="$DB_PASS" \
                          -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
                          -e SERVER_PORT=8092 \
                          oneyear-backend:latest
                    '''
                }
            }
        }

        stage('Smoke Test') {
            steps {
                // "localhost" inside the Jenkins container is Jenkins itself,
                // not the host, so ask the app from inside its own container.
                // Retries because Spring Boot + Neon can take a while to start.
                sh '''
                    for i in $(seq 1 12); do
                        if docker exec oneyear-backend wget -q -O /dev/null http://localhost:8092/api/users; then
                            echo "App is up"
                            exit 0
                        fi
                        echo "Waiting for app to start... ($i/12)"
                        sleep 10
                    done
                    echo "App did not become healthy. Last logs:"
                    docker logs --tail 60 oneyear-backend
                    exit 1
                '''
            }
        }
    }

    post {
        success {
            echo "Pipeline succeeded: build #${BUILD_NUMBER}"
        }
        failure {
            echo "Pipeline failed: build #${BUILD_NUMBER} - check the stage logs above."
        }
        always {
            archiveArtifacts artifacts: 'target/surefire-reports/**', allowEmptyArchive: true
            cleanWs()
        }
    }
}

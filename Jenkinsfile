pipeline {
    agent any

    environment {
        IMAGE_NAME = 'oneyear-backend'
        // Must match the name given to the server under
        // Manage Jenkins -> System -> SonarQube servers.
        SONARQUBE_SERVER = 'MySonarQube'
    }

    triggers {
        // Registers this job to react to GitHub's webhook push events.
        // Requires the "GitHub" plugin and a webhook configured on the
        // repo (see setup steps). Needs one manual build first before
        // Jenkins knows this job exists for the webhook to reach.
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
                // This instance is small; stopping the running app
                // containers (not Jenkins/SonarQube, which the pipeline
                // itself needs) buys headroom for the Maven compile.
                // They come back up in the Deploy stage regardless.
                sh 'docker stop oneyear-backend oneyear-mysql || true'
            }
        }

        stage('Build & Unit Test') {
            steps {
                sh './mvnw clean verify'
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
                    waitForQualityGate abortPipeline: true
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
                // Brings backend + mysql back up (or fresh up) using the
                // image just built.
                sh 'docker compose up -d --build'
            }
        }

        stage('Smoke Test') {
            steps {
                // Give Spring Boot a moment to actually finish starting
                // before hitting it.
                sh 'sleep 15 && curl -f http://localhost:8092/api/users'
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
            cleanWs()
        }
    }
}

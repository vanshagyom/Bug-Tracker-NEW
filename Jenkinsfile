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
                sh 'docker compose up -d --build'
            }
        }

        stage('Smoke Test') {
            steps {
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
            archiveArtifacts artifacts: 'target/surefire-reports/**', allowEmptyArchive: true
            cleanWs()
        }
    }
}

#!groovy

def gitUrl = "https://github.com/utham9/easy-peasy.git"
def branchName = "master"

pipeline {

/*
    agent {
       label ''
    }
    */

    agent any

    options {
        timeout(time: 12, unit: 'HOURS')
        buildDiscarder(
                logRotator(
                        numToKeepStr: '5',
                        daysToKeepStr: '7',
                        artifactDaysToKeepStr: '7',
                        artifactNumToKeepStr: '5'
                )
        )
    }

    stages {
        stage('clone') {
            steps {
                git branch: branchName,
                        credentialsId: '',
                        url: gitUrl
            }
        }
        stage('docker') {
            steps {
                script {
                    sh "sudo systemctl start docker"
                    sh "docker-compose -d selenium-grid.yml up"
                }
            }
        }

        stage('test') {
            steps {
                script {
                    sh "docker build Dockerfile ."
                }
            }
        }

        stage('wind-down') {
            steps {
                script {
                    sh "docker-compose -d selenium-grid.yml down"
                    sh "sudo systemctl stop docker"
                }
            }
        }
    }

    post {
        always {
            echo "Run on success"
        }
        success {
            echo "Run on success"
        }
        failure {
            echo "Run on failure"
        }
    }


}
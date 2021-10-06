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
    /*
    parameters {
        string (name:'TEST', defaultValue : 'LOGIN', description :'test case')
        choice (name: 'ENV', choices:['QA','STAGE'],description: 'Environment')
        booleanParam (name: 'DRY RUN',defaultValue: false,description: 'DRY RUN')
    }
    */

    stages {
        stage('clone') {
            steps {
                git branch: branchName,
                        credentialsId: '0c957041-83ab-4509-afc6-78b8ebf17b06',
                        url: gitUrl
            }
        }
        stage('docker') {
            steps {
                script {
                    sh "docker"
                }
            }
        }

        stage('test') {
            steps {
                script {
                    sh ""
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
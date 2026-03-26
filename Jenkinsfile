def BROWSER_LIST = ['chrome', 'firefox', 'edge']

pipeline {
    agent none

    stages {

        stage('Test') {
            matrix {
                axes {
                    axis {
                        name 'BROWSER'
                        values 'chrome', 'firefox', 'edge'
                    }
                }
                agent {
                    label "docker-${BROWSER}"
                }
                stages {
                    stage('Smoke') {
                        steps {
                            sh "mvn clean test -Dgroups=\"smoke\" -Dbrowser=\"${BROWSER}\""
                        }
                    }
                    stage('Critical path') {
                        steps {
                            sh "mvn clean test -Dgroups=\"critical-path\" -Dbrowser=\"${BROWSER}\" -Dmaven.test.failure.ignore=true"
                        }
                    }
                    stage('Regression') {
                        steps {
                            sh "mvn clean test -Dgroups=\"regression\" -Dbrowser=\"${BROWSER}\" -Dmaven.test.failure.ignore=true"
                        }
                    }
                }

                post {
                    always {
                        stash name: "allure-results-${BROWSER}", includes: 'target/allure-results/**', allowEmpty: true
                    }
                }
            }
        }

        stage('Allure reporting') {
            agent any
            steps {
                script {
                    for (String browserName: BROWSER_LIST) {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            unstash "allure-results-${browserName}"
                        }
                    }
                }
            }

            post {
                always {
                    allure jdk: '', results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}
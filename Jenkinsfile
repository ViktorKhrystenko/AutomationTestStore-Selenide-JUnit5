def BROWSER_LIST = ['chrome', 'firefox', 'edge']

pipeline {
    agent none

    stages {

        stage('Test') {
        matrix {
            axes {
                axis {
                    name BROWSER
                    values BROWSER_LIST
                }
            }
            agent {
                label "docker-${BROWSER}"
            }
            stages {
                stage('Smoke') {
                    sh "mvn clean test -Dgroups=\"smoke\" -Dbrowser=\"${BROWSER}\""
                }
                stage('Critical path') {
                    sh "mvn clean test -Dgroups=\"critical-path\" -Dbrowser=\"${BROWSER}\" -Dmaven.test.failure.ignore=true"

                }
                stage('Regression') {
                    sh "mvn clean test -Dgroups=\"reggression\" -Dbrowser=\"${BROWSER}\" -Dmaven.test.failure.ignore=true"

                }
            }

            post {
                always {
                    stash name: "allure-results-${BROWSER}", include: 'target/allure-results/**', allowEmpty: true
                }
            }
        }

        stage {
            agent any
            step {
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
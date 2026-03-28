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
                            sh "mvn test -Drun.target=\"jenkins-docker-agent\" -Dgroups=\"smoke\" -Dbrowser=\"${BROWSER}\""
                        }
                    }
                    stage('Critical path') {
                        steps {
                            sh "mvn test -Drun.target=\"jenkins-docker-agent\" -Dgroups=\"critical-path\" -Dbrowser=\"${BROWSER}\" -Dmaven.test.failure.ignore=true"
                        }
                    }
                    stage('Regression') {
                        steps {
                            sh "mvn test -Drun.target=\"jenkins-docker-agent\" -Dgroups=\"regression\" -Dbrowser=\"${BROWSER}\" -Dmaven.test.failure.ignore=true"
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
    }

    post {
        always {
            agent any
            script {
                for (String browserName: BROWSER_LIST) {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        unstash "allure-results-${browserName}"
                    }
                }
            }
            allure jdk: '', results: [[path: 'target/allure-results']]
        }
    }
}
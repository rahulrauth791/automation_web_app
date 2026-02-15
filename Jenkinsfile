pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    environment {
        CONFIG_FILE = "${WORKSPACE}/src/test/resources/config.properties"
    }

    parameters {
        string(
            name: 'BROWSER',
            defaultValue: 'chrome',
            description: 'Browser to run tests (chrome, firefox, edge, safari, chrome-headless)'
        )
        booleanParam(
            name: 'PARALLEL_BROWSERS',
            defaultValue: false,
            description: 'Run tests in parallel on chrome and firefox'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    def browsers = params.PARALLEL_BROWSERS
                            ? ['chrome', 'firefox']
                            : [params.BROWSER]

                    def branches = [:]

                    browsers.each { browser ->
                        branches[browser] = {
                            stage("Test - ${browser}") {
                                sh """
                                   mvn -B test \
                                   -Dconfig.file=${CONFIG_FILE} \
                                   -Dbrowser=${browser}
                                """
                            }
                        }
                    }

                    parallel branches
                }
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/surefire-reports/*.xml, test-output/**, src/test/resources/config.properties',
                             allowEmptyArchive: true
        }
        cleanup {
            echo 'Cleanup step (if needed)'
        }
    }
}

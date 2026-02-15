pipeline {
    agent any

    environment {
        CONFIG_FILE = "${WORKSPACE}/src/test/resources/config.properties"
    }

    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser to run tests (chrome, firefox, edge, safari). Append -headless for headless, e.g., chrome-headless')
        booleanParam(name: 'PARALLEL_BROWSERS', defaultValue: false, description: 'If true, run tests in parallel for chrome and firefox')
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
                    def browsers = params.PARALLEL_BROWSERS ? ['chrome','firefox'] : [params.BROWSER]
                    def branches = [:]
                    for (int i = 0; i < browsers.size(); i++) {
                        def browser = browsers[i]
                        branches[browser] = {
                            stage("Test - ${browser}") {
                                // ensure config.file points to repository config by default; can be overridden via -Dconfig.file
                                sh "mvn -B test -Dconfig.file=${CONFIG_FILE} -Dbrowser=${browser}"
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
            // publish junit results and archive artifacts/screenshots
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/surefire-reports/*.xml, test-output/**, src/test/resources/config.properties', allowEmptyArchive: true
        }
        cleanup {
            echo 'Cleanup step (if needed)'
        }
    }
}
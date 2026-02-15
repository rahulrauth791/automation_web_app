pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['Login', 'Sanity', 'Regression'],
            description: 'Select TestNG suite'
        )

        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Browser'
        )
    }

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Resolve Test Suite') {
            steps {
                script {
                    def suiteMap = [
                        Login      : 'src/test/resources/login.xml',
                        Sanity     : 'src/test/resources/sanity.xml',
                        Regression : 'src/test/resources/regression.xml'
                    ]

                    env.SUITE_FILE = suiteMap[params.TEST_SUITE]

                    if (!env.SUITE_FILE) {
                        error "Invalid TEST_SUITE selected: ${params.TEST_SUITE}"
                    }

                    echo "Using suite file: ${env.SUITE_FILE}"
                }
            }
        }

        stage('Run Tests') {
            steps {
                sh """
                    mvn clean test \
                    -Dsurefire.suiteXmlFiles=${env.SUITE_FILE} \
                    -Dbrowser=${params.BROWSER}
                """
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/**', allowEmptyArchive: true
        }
    }
}

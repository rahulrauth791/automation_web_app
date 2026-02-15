pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['Login', 'Online', 'Youtube'],
            description: 'Select TestNG suite'
        )

        choice(
            name: 'ENV',
            choices: ['staging', 'preprod', 'prod'],
            description: 'Select environment'
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
                        Online     : 'src/test/resources/Online.xml',
                        Youtube    : 'src/test/resources/Youtube.xml'
                    ]

                    env.SUITE_FILE = suiteMap[params.TEST_SUITE]

                    if (!env.SUITE_FILE) {
                        error "Invalid TEST_SUITE selected: ${params.TEST_SUITE}"
                    }

                    echo "Suite      : ${params.TEST_SUITE}"
                    echo "Environment: ${params.ENV}"
                    echo "Browser    : ${params.BROWSER}"
                }
            }
        }

        stage('Run Tests') {
            steps {
                sh """
                    mvn clean test \
                    -Dsurefire.suiteXmlFiles=${env.SUITE_FILE} \
                    -Dbrowser=${params.BROWSER} \
                    -Denv=${params.ENV}
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

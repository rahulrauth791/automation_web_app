pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['Login', 'Online', 'Youtube'],
            description: 'Select test suite to execute'
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

        stage('Resolve Suite') {
            steps {
                script {
                    def suiteMap = [
                        'Smoke'      : 'src/test/resources/testng-smoke.xml',
                        'Sanity'     : 'src/test/resources/testng-sanity.xml',
                        'Regression' : 'src/test/resources/testng-regression.xml',
                        'Full'       : 'src/test/resources/testng-full.xml'
                    ]

                    env.SUITE_FILE = suiteMap[params.TEST_SUITE]
                    echo "Running suite: ${env.SUITE_FILE}"
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

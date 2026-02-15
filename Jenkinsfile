pipeline {
    agent any

    parameters {
        string(
            name: 'TEST_SUITE',
            defaultValue: 'src/test/resources/testng.xml',
            description: 'Path of TestNG suite file'
        )
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Browser to run tests'
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

        stage('Run Selected TestNG Suite') {
            steps {
                sh """
                    mvn clean test \
                    -Dsurefire.suiteXmlFiles=${params.TEST_SUITE} \
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

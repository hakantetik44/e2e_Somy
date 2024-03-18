pipeline {
    agent any

    environment {
        JIRA_API_URL = 'https://your-jira-instance.atlassian.net/rest/api/latest/issue/SUP-6/import'
        JIRA_AUTHORIZATION = 'Basic <base64-encoded-username-and-api-token>'
        CUCUMBER_JSON_PATH = '/Users/macbook/IdeaProjects/e2e_Somy/target/cucumber.json'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean test -Dtest=RunCucumberTest'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn verify'
            }
        }

        stage('Import Cucumber Report to Jira') {
            steps {
                script {
                    def cucumberJsonContent = readFile(file: env.CUCUMBER_JSON_PATH).trim()

                    def response = httpRequest(
                        httpMode: 'POST',
                        url: env.JIRA_API_URL,
                        authentication: 'BASIC',
                        requestBody: "{\"data\": \"$cucumberJsonContent\"}",
                        customHeaders: [[name: 'Authorization', value: env.JIRA_AUTHORIZATION]],
                        contentType: 'application/json'
                    )

                    if (response.status != 200) {
                        error "Fail: Status code ${response.status} is not in the accepted range: 200 while calling ${env.JIRA_API_URL}"
                    }
                }
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/TEST-*.xml'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }

        success {
            echo 'Build succeeded!'
        }

        failure {
            echo 'Build failed!'
        }
    }
}

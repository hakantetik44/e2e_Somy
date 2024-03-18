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

        stage('Test') {
            steps {
                sh 'mvn clean test -Dtest=RunCucumberTest'
            }
        }

        stage('Import Cucumber Report to Jira') {
            steps {
                httpRequest(
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'POST',
                    requestBody: readFile('path/to/cucumber_report.json'),
                    url: env.JIRA_API_URL,
                    authentication: env.JIRA_AUTHORIZATION
                )
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

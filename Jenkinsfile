import groovy.json.JsonOutput

pipeline {
    agent any

    environment {
        apiToken = credentials('jira-api-token') // Jira API tokenini credential'dan al
        apiUrl = 'https://myprojecthepsiburada.atlassian.net/rest/raven/1.0/import/execution/cucumber/SUP-6' // Jira API URL'si
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout işlemleri buraya gelecek
            }
        }

        stage('Test') {
            steps {
                // Test işlemleri buraya gelecek
            }
        }

        stage('Copy Cucumber Report') {
            steps {
                // Cucumber raporunu kopyala işlemleri buraya gelecek
            }
        }

        stage('Publish Cucumber Report to Jira') {
            steps {
                script {
                    def response = httpRequest(
                        acceptType: 'APPLICATION_JSON',
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        requestBody: readFile('cucumber.json'),
                        url: apiUrl,
                        validResponseCodes: '200'
                    )
                    if (response.status != 200) {
                        error("Fail: Status code ${response.status} is not in the accepted range: 200")
                    }
                }
            }
        }
    }
}


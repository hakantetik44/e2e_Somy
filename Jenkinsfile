pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Git checkout işlemi
                checkout scm
            }
        }
        stage('Test') {
            steps {
                // Test işlemleri (örneğin Maven)
                sh 'mvn clean test -Dtest=RunCucumberTest'
            }
        }
        stage('Copy Cucumber Report') {
            steps {
                // Cucumber raporunu kopyala
                sh 'cp /Users/macbook/IdeaProjects/e2e_Somy/target/cucumber.json ./'
            }
        }
        stage('Publish Cucumber Report to Jira') {
            steps {
                // JsonOutput sınıfını içe aktarma işlemine gerek yok
                script {
                    def jiraBaseUrl = 'https://myprojecthepsiburada.atlassian.net'
                    def issueKey = 'SUP-6'
                    def apiKey = 'ATATT3xFfGF0ZP8IOOf44O5w3keYm4P_yN3eFyYddhiZEcgYuF_cK6ETVXY02DPKGvaDpnDtZMUDF8ESPFh7r4OwTM18JvAk5Rh9jsbJaEwe_1DRQaV5H8jJ5ROTZExTfbr87zWsaHWCvZKyRPgpdR6STYJvKCCektL6sOnAfQN7BTxOoqDceP4=7EB171E2'

                    def apiUrl = "${jiraBaseUrl}/rest/raven/1.0/import/execution/cucumber"
                    def headers = [
                        'Content-Type': 'application/json',
                        'Authorization': "Basic ${apiKey}"
                    ]

                    def cucumberReport = readFile('cucumber.json')
                    def requestBody = [
                        "issueKeys": [issueKey],
                        "results": [
                            "format": "cucumber",
                            "cucumberResults": cucumberReport
                        ]
                    ]

                    def response = httpRequest(
                        acceptType: 'APPLICATION_JSON',
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        requestBody: JsonOutput.toJson(requestBody), // JsonOutput sınıfını kullanarak dönüşüm yapılıyor
                        responseHandle: 'NONE',
                        url: apiUrl,
                        validResponseCodes: '200'
                    )

                    if (response.status != 200) {
                        error "Failed to publish Cucumber report to Jira. Status code: ${response.status}, Response: ${response.content}"
                    } else {
                        echo "Cucumber report published to Jira successfully."
                    }
                }
            }
        }
    }
}

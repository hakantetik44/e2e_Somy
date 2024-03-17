pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Start Appium Server') {
            steps {
                sh 'appium &'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn clean test -Dtest=RunCucumberTest'
            }
        }

        stage('Copy Cucumber Report') {
            steps {
                sh 'cp /Users/macbook/IdeaProjects/e2e_Somy/target/cucumber.json ./'
            }
        }

        stage('Publish Cucumber Report') {
            steps {
                script {
                    def jsonReportDirectory = "target/cucumber-reports"
                    def outputDirectory = "target"
                    def fileIncludePattern = "*.json"
                    def trendsLimit = 0
                    def ignoreBadSteps = true
                    def parallelTesting = false

                    publishCucumberReports(
                        jsonReportDirectory: jsonReportDirectory,
                        outputDirectory: outputDirectory,
                        fileIncludePattern: fileIncludePattern,
                        trendsLimit: trendsLimit,
                        ignoreBadSteps: ignoreBadSteps,
                        parallelTesting: parallelTesting
                    )
                }
            }
        }

        stage('Stop Appium Server') {
            steps {
                sh 'pkill -f "appium"'
            }
        }

        stage('Import to Xray') {
            steps {
                script {
                    def jiraUrl = "https://myprojecthepsiburada.atlassian.net"
                    def jiraIssueKey = "SUP-6"
                    def jiraApiKey = "ATATT3xFfGF0ZP8IOOf44O5w3keYm4P_yN3eFyYddhiZEcgYuF_cK6ETVXY02DPKGvaDpnDtZMUDF8ESPFh7r4OwTM18JvAk5Rh9jsbJaEwe_1DRQaV5H8jJ5ROTZExTfbr87zWsaHWCvZKyRPgpdR6STYJvKCCektL6sOnAfQN7BTxOoqDceP4=7EB171E2"
                    def cucumberReport = "cucumber.json"

                    // Xray'ye test sonuçlarını yükleme komutu
                    sh "curl -H 'Content-Type: application/json' -H 'Authorization: APIKEY ${jiraApiKey}' -X POST -d @${cucumberReport} ${jiraUrl}/rest/raven/1.0/import/execution/${jiraIssueKey}"
                }
            }
        }
    }
}

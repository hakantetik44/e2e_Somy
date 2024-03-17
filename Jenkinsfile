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
                script {
                    sh 'appium &'
                }
            }
        }

        stage('Test') {
            steps {
                // Maven ile testlerin çalıştırılması
                sh 'mvn clean test -Dtest=RunCucumberTest'
            }
        }

        stage('Publish Cucumber Report') {
            steps {
                // Cucumber raporunun yayınlanması
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

        stage('Execute Tests on Jira Xray') {
            steps {
                script {
                    // Jira Xray REST API'sine HTTP isteği yaparak testleri yürüt
                    // Örnek bir curl komutu kullanabilirsin veya Jenkins'in HTTP Request Plugin'i gibi bir eklentiyle istek yapabilirsin
                    // Bu adımı uygulayabilmek için Jira Xray API belgelerini incelemen gerekebilir
                    // İsteği başlatmak için gerekli kimlik doğrulaması ve diğer parametreleri de belirtmelisin
                }
            }
        }

        stage('Stop Appium Server') {
            steps {
                // Appium sunucusunu sonlandır
                sh 'pkill -f "appium"'
            }
        }
    }
}

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
                    // Terminalde Appium'u başlatmak için gerekli komutu buraya yazın
                    sh 'appium &'
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn clean test -Dtest=RunCucumberTest'
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
                // Appium'u sonlandırmak için gerekli komutu buraya yazın
                sh 'pkill -f "appium"'
            }
        }
    }
}

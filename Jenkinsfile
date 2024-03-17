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
                sh 'appium' // Appium server'ı başlatmak için gerekli komutu buraya yazın
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
    }
}

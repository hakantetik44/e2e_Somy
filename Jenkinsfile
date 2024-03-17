pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
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

                    step([$class: 'CucumberReportPublisher',
                          jsonReportDirectory: jsonReportDirectory,
                          outputDirectory: outputDirectory,
                          fileIncludePattern: fileIncludePattern,
                          trendsLimit: trendsLimit,
                          ignoreBadSteps: ignoreBadSteps,
                          parallelTesting: parallelTesting])
                }
            }
        }
    }
}

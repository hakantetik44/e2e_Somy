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

       stage('Publish Cucumber Report') {
           steps {
               sh 'cp /Users/macbook/IdeaProjects/e2e_Somy/target/cucumber.json .'
               script {
                   def jsonReportDirectory = "${env.WORKSPACE}"
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
    }
}

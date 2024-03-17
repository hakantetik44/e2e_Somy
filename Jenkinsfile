pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Git repository'yi kontrol et
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Maven clean install komutunu çalıştır
                sh 'mvn clean install'
            }
        }

        stage('Start Appium') {
            steps {
                // Appium'u başlat
                script {
                    try {
                        sh 'appium --port 4725' // Appium'u 4725 portunda başlat
                    } catch (Exception e) {
                        echo "Appium already running or port in use: $e"
                    }
                }
            }
        }

        stage('Run Tests') {
            steps {
                // Cucumber testlerini çalıştır
                sh 'mvn test -Dcucumber.publish.enabled=true'
            }
        }

        stage('Publish Cucumber Report') {
            steps {
                script {
                    // Cucumber raporlarını oluşturmak için maven-cucumber-reporting eklentisini kullan
                    def jsonReportDirectory = "/Users/macbook/IdeaProjects/e2e_Somy/target"
                    def outputDirectory = "/Users/macbook/IdeaProjects/e2e_Somy/target"
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

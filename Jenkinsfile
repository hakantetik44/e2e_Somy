pipeline {
    agent any

    stages {
        stage('Start Appium') {
            steps {
                // Uygulamayı başlatmak için Appium'u çalıştır
                sh 'appium'
            }
        }

        stage('Run Tests') {
            steps {
                // Git repository'yi kontrol et
                checkout scm

                // Maven clean install komutunu çalıştır
                sh 'mvn clean install'

                // Cucumber raporlarını oluşturmak için Cucumber testlerini çalıştır
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

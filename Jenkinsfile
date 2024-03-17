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

        stage('Test') {
            steps {
                // Cucumber raporlarını oluşturmak için Cucumber testlerini çalıştır
                sh 'mvn test -Dcucumber.publish.enabled=true'
            }
        }
    }

    post {
        always {
            // Cucumber raporlarını yayınlamak için post-build aşaması
            cucumberReport()
        }
    }
}

def cucumberReport() {
    // Cucumber raporlarını oluşturmak için maven-cucumber-reporting eklentisini kullan
    step([$class: 'CucumberReportPublisher',
          jsonReportDirectory: '/Users/macbook/IdeaProjects/e2e_Somy/target',
          outputDirectory: '/Users/macbook/IdeaProjects/e2e_Somy/target',
          fileIncludePattern: '*.json',
          trendsLimit: 0,
          ignoreBadSteps: true,
          parallelTesting: false])
}

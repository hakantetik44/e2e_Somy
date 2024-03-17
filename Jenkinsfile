pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Git repository'i kontrol et
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
           cucumber(fileIncludePattern: '**/cucumber*.json')
        }
    }
}

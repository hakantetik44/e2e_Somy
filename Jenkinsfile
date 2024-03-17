pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Maven projesini temizle ve derle
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                // Cucumber testlerini çalıştır ve raporu oluştur
                sh 'mvn test -Dcucumber.publish.enabled=true'
            }
        }
        stage('Deploy') {
            steps {
                // Uygulamanın dağıtımı için gerekli adımları buraya ekleyin
            }
        }
    }
}

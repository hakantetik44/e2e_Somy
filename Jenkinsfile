pipeline {
    agent any

    stages {
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
        stage('Deploy') {
            steps {
              sh 'mvn package'
                // Uygulamanın dağıtımı için gerekli adımları buraya ekleyin
            }
        }
    }
}

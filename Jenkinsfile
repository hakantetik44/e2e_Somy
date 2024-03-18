pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Git repository'i klonlama adımı
                checkout([$class: 'GitSCM',
                          branches: [[name: '*/main']],
                          doGenerateSubmoduleConfigurations: false,
                          extensions: [],
                          submoduleCfg: [],
                          userRemoteConfigs: [[url: 'git@github.com:hakantetik44/e2e_Somy.git']]
                         ])
            }
        }

        stage('Test') {
            steps {
                // Test adımları buraya gelecek (örneğin: Maven, Gradle, vb.)
                sh 'mvn clean test -Dtest=RunCucumberTest' // Örnek Maven test komutu
            }
        }

        stage('Copy Cucumber Report') {
            steps {
                // Cucumber raporunu kopyalama adımı
                sh 'cp /Users/macbook/IdeaProjects/e2e_Somy/target/cucumber.json ./'
            }
        }

        stage('Publish Cucumber Report to Jira') {
            steps {
                // Cucumber raporunu Jira'ya yayınlama adımı
                script {
                    def response = httpRequest(
                        acceptType: 'APPLICATION_JSON',
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        requestBody: readFile('cucumber.json'),
                        url: 'https://myprojecthepsiburada.atlassian.net/jira/servicedesk/projects/SUP/queues/custom/1/rest/raven/1.0/import/execution/cucumber/SUP-6',
                        validResponseCodes: '200'
                    )
                    if (response.status != 200) {
                        error("Fail: Status code ${response.status} is not in the accepted range: 200")
                    }
                }
            }
        }
    }
}

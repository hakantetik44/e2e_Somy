pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
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
                sh 'mvn clean test -Dtest=RunCucumberTest'
            }
        }

        stage('Copy Cucumber Report') {
            steps {
                sh 'cp /Users/macbook/IdeaProjects/e2e_Somy/target/cucumber.json ./'
            }
        }

        stage('Publish Cucumber Report to Jira') {
            steps {
                script {
                    def response = httpRequest(
                        acceptType: 'APPLICATION_JSON',
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        requestBody: readFile('cucumber.json'),
                        url: 'https://myprojecthepsiburada.atlassian.net/browse/SUP-6',
                        validResponseCodes: '200'
                    )

                    if (response.status < 200 || response.status >= 300) {
                        error("Fail: Status code ${response.status} is not in the accepted range: 200")
                    }
                }
            }
        }
    }
}

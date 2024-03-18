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
                       validResponseCodes: '200' // or valid status codes for your case
                   )

                   // If the response code is not in the accepted range
                   if (response.status < 200 || response.status >= 300) {
                       // Check if the response contains a Location header (indicating a redirect)
                       if (response.headers['Location']) {
                           // Extract the redirected URL
                           def redirectedUrl = response.headers['Location']
                           echo "Redirected URL: ${redirectedUrl}"

                           // Make a new HTTP request to the redirected URL
                           def redirectedResponse = httpRequest(
                               acceptType: 'APPLICATION_JSON',
                               contentType: 'APPLICATION_JSON',
                               httpMode: 'POST',
                               requestBody: readFile('cucumber.json'),
                               url: redirectedUrl,
                               validResponseCodes: '200' // or valid status codes for your case
                           )

                           // Check the status code of the redirected response
                           if (redirectedResponse.status != 200) {
                               error("Fail: Status code ${redirectedResponse.status} is not in the accepted range: 200")
                           }
                       } else {
                           // If no Location header is present, then it's not a redirect error
                           error("Fail: Status code ${response.status} is not in the accepted range: 200")
                       }
                   }
               }
           }
       }

}

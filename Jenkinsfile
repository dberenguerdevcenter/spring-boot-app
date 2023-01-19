pipeline{

	agent {
        node {
            label "java-node"
        }
    }

    stages {

        stage('Build') {
          steps {
               sh "mvn clean install -DskipTests"
          }
        }

        stage('SonarQube analysis') {
          steps {
            withSonarQubeEnv(credentialsId: "sonarqube-credentials", installationName: "sonarqube-server"){
                sh "mvn clean verify sonar:sonar -DskipTests"
            }
        }

        stage('Quality Gate') {
          steps {
            timeout(time: 10, unit: "MINUTES") {
              script {
                def qg = waitForQualityGate(webhookSecretId: 'sonarqube-credentials')
                if (qg.status != 'OK') {
                   error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
              }
            }
          }
        }

	}

}
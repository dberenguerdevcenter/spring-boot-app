def versionPom = ""
pipeline{
	agent {
        node {
            label "java-node"
        }
    }

    stages {
        stage('Build Project') {
          steps {
               sh "mvn clean install -DskipTests"
          }
        }
	}

}
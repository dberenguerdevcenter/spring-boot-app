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
	}

}
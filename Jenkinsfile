pipeline {
    agent {
        node {
            label "nodo-java"
        }
    }

    stages{
        stage("Test"){
            steps{
                sh "mvn test"
            }
        }

        stage("Build"){
            steps{
                sh "mvn clean package -DskipTests"
            }
        }
    }

}
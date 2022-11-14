pipeline {
    agent {
        node {
            label "nodo-java"
        }
    }

    stages{
        stage("Build"){
            steps{
                sh "mvn clean package -DskipTests"
            }
        }
    }

}
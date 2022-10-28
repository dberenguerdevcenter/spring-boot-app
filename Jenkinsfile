pipeline{

    agent {
        node {
            label "nodo-java"
        }
    }

    environment {
        registryCredential='docker-hub-credentials'
        registryBackend = 'franaznarteralco/backend-demo'
    }

    stages {

//         stage('SonarQube analysis') {
//           steps {
//             withSonarQubeEnv(credentialsId: "sonarqube-credentials", installationName: "sonarqube-server"){
//                 sh "mvn clean verify sonar:sonar -DskipTests"
//             }
//           }
//         }
//
//         stage('Quality Gate') {
//             steps {
//                 timeout(time: 10, unit: "MINUTES") {
//                     script {
//                         def qg = waitForQualityGate(webhookSecretId: 'sonarqube-credentials')
//                         if (qg.status != 'OK') {
//                             error "Pipeline aborted due to quality gate failure: ${qg.status}"
//                         }
//                     }
//                 }
//             }
//         }
//
//         stage('SonarQube analysis') {
//             steps {
//                 sh "mvn clean install -DskipTests"
//             }
//         }
//
//         stage('Push Image to Docker Hub') {
//             steps {
//                 script {
//                     dockerImage = docker.build registryBackend + ":$BUILD_NUMBER"
//                     docker.withRegistry( '', registryCredential) {
//                         dockerImage.push()
//                     }
//                 }
//             }
//         }
//
//         stage('Push Image latest to Docker Hub') {
//             steps {
//                 script {
//                     dockerImage = docker.build registryBackend + ":latest"
//                     docker.withRegistry( '', registryCredential) {
//                         dockerImage.push()
//                     }
//                 }
//             }
//         }
//
//         stage("Deploy to K8s"){
//             steps{
//                 script {
//                     if(fileExists("configuracion")){
//                         sh 'rm -r configuracion'
//                     }
//                 }
//                 sh 'git clone https://github.com/dberenguerdevcenter/kubernetes-helm-docker-config.git configuracion --branch test-implementation'
//                 sh 'kubectl apply -f configuracion/kubernetes-deployment/spring-boot-app/manifest.yml -n default --kubeconfig=configuracion/kubernetes-config/config'
//             }
//         }
//
//         stage ("Run API Test") {
//             steps{
//                 node("node-nodejs"){
//                     script {
//                         if(fileExists("spring-boot-app")){
//                             sh 'rm -r spring-boot-app'
//                         }
//                         sleep 15 // seconds
//                         sh 'git clone https://github.com/dberenguerdevcenter/spring-boot-app.git spring-boot-app --branch api-test-implementation'
//                         sh 'newman run spring-boot-app/src/main/resources/bootcamp.postman_collection.json --reporters cli,junit --reporter-junit-export "newman/report.xml"'
//                         junit "newman/report.xml"
//                     }
//                 }
//             }
//         }

        stage ("Run Performance Test") {
            steps{
                script {

                    if(fileExists("jmeter-docker")){
                       sh 'rm -r jmeter-docker'
                    }

                    sh 'git clone https://github.com/FranAznarTeralco/jmeter-docker.git'

                     dir('jmeter-docker') {
                        sh 'chmod +775 ./build.sh && chmod +775 ./run.sh && chmod +775 ./entrypoint.sh'
                        sh 'tar -czvf apache-jmeter-5.2.1.tgz apache-jmeter-5.2.1'
                        sh './build.sh'
                        sh 'cp ../src/main/resources/perform_test_bootcamp.jmx .'
                        sh './run.sh -n -t perform_test_bootcamp.jmx -l perform_test_bootcamp.jtl -Jthreads=2 -Jrampup=1 -Jduration=10'
                     }

                    step([$class: 'ArtifactArchiver', artifacts: 'perform_test_bootcamp.jtl'])
                }
            }
        }
    }

	post {
        always {
            sh "docker logout"
        }
	}

}
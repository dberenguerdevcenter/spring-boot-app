def versionPom = ""
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
//           steps {
//             timeout(time: 10, unit: "MINUTES") {
//               script {
//                 def qg = waitForQualityGate(webhookSecretId: 'sonarqube-credentials')
//                 if (qg.status != 'OK') {
//                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
//                 }
//               }
//             }
//           }
//         }

        stage('SonarQube analysis') {
          steps {
               sh "mvn clean install -DskipTests"
          }
        }

        stage('Push Image to Docker Hub') {
          steps {
            script {
              dockerImage = docker.build registryBackend + ":$BUILD_NUMBER"
              docker.withRegistry( '', registryCredential) {
                dockerImage.push()
              }
            }
          }
        }

        stage('Push Image latest to Docker Hub') {
          steps {
            script {
              dockerImage = docker.build registryBackend + ":latest"
              docker.withRegistry( '', registryCredential) {
                dockerImage.push()
              }
            }
          }
        }

		stage("Deploy to K8s"){
			steps{
                script {
                  if(fileExists("configuracion")){
                    sh 'rm -r configuracion'
                  }
                }

				sh 'git clone https://github.com/dberenguerdevcenter/kubernetes-helm-docker-config.git configuracion --branch test-implementation'
				sh 'kubectl apply -f configuracion/kubernetes-deployment/spring-boot-app/manifest.yml -n default --kubeconfig=configuracion/kubernetes-config/config'
			}
		}

		stage ("Run API Test") {
            node {
                label "node-nodejs"
            }

            script {
                sh 'npm install newman'
            }

            def time = 15
            echo "Waiting ${SLEEP_TIME_IN_SECONDS} seconds for deployment to complete prior starting smoke testing"
            sleep time.toInteger() // seconds

            script {
                sh 'newman run src/main/resources/bootcamp.postman_collection.json'
            }
        }

	}
	post {
		always {
			sh "docker logout"
		}
	}
}
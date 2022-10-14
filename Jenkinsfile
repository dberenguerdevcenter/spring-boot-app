def versionPom = ""
pipeline{
	agent {
        node {
            label "nodo-java"
        }
    }
	environment {
        DOCKER_IMAGE_NAME="franaznarteralco/backend-demo"
		DOCKERHUB_CREDENTIALS=credentials("docker-hub-credentials")
	}
	stages {
        stage('SonarQube analysis') {
          steps {
            withSonarQubeEnv(credentialsId: "sonarqube-credentials", installationName: "sonarqube-server"){
                sh "mvn clean verify sonar:sonar -DskipTests"
            }
          }
        }

        stage('Quality Gate') {
          steps {
            timeout(time: 10, unit: "MINUTES") {
              script {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                   error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
              }
            }
          }
        }

		stage("Build image and push to Docker Hub") {
			steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh "docker build -t $DOCKER_IMAGE_NAME:${versionPom} ."
                sh "docker push $DOCKER_IMAGE_NAME:${versionPom}"
                sh "docker build -t $DOCKER_IMAGE_NAME:latest ."
                sh "docker push $DOCKER_IMAGE_NAME:latest"
			}
		}
		stage("Deploy to K8s")
		{
			steps{
				sh "git clone https://github.com/dberenguerdevcenter/kubernetes-helm-docker-config.git configuracion --branch test-implementation"
				sh "kubectl apply -f configuracion/kubernetes-deployments/spring-boot-app/deployment.yaml --kubeconfig=configuracion/kubernetes-config/config"
			}
		}
	}
	post {
		always {
			sh "docker logout"
		}
	}
}
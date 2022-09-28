def versionPom = ""
pipeline{
	agent { 
        node { 
            label "nodo-java"
        }
    }
	environment {
		NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.49.6:8081"
        NEXUS_REPOSITORY = "bootcamp"
        NEXUS_CREDENTIAL_ID = "nexus"
        DOCKER_IMAGE_NAME="dberenguerdevcenter/spring-boot-app"
		DOCKERHUB_CREDENTIALS=credentials("docker-hub")
	}
	stages {
		stage("Build") {
			steps {
                sh "mvn clean package -DskipTests"
			}
		}
        stage("Test") {
			steps {
                sh "mvn test"
				jacoco()
                junit "target/surefire-reports/*.xml"
			}
		}
		stage("Publish to Nexus") {
            steps {
                pom = readMavenPom file: "pom.xml"
                versionPom = "${pom.version}"
                sh "mvn deploy -DskipTests"
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
				sh "git clone https://github.com/dberenguerdevcenter/kubernetes-helm-docker-config.git configuracion --branch demo-java"
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
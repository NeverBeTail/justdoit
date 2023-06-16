pipeline {

	environment {
            imageName = "neverbetail/justdoit" //docker hub id와 repository 이름
            registryCredential = 'DockerHub_credentials' // jenkins에 등록해 놓은 docker hub credentials 이름
            dockerImage = ''
    }

    agent any

    stages {
        stage('Docker-build'){
             steps {
                 echo 'Build Docker'
                 sh 'cp /var/jenkins_home/workspace/justdoit/justdoit-0.0.1-SNAPSHOT.jar /var/jenkins_home/workspace/justdoit_pipe/'
                 script {
                    dockerImage = docker.build imageName

                 }
             }
        }
        stage('Login'){
            steps{
                  sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin' // docker hub 로그인
            }
        }
        stage('docker-push'){
             steps{
                   echo 'Push Docker'
                   script {
                        docker.withRegistry('', registryCredential){
                        dockerImage.push("1.0")
                        }
                   }
             }
        }
	}
}
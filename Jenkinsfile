pipeline {

	environment {
            imageName = "neverbetail/justdoit" //docker hub id와 repository 이름
            registryCredential = 'DockerHub_credentials' // jenkins에 등록해 놓은 docker hub credentials 이름
            dockerImage = ''
    }

    agent any

    stages {
        stage('docker-build'){
                    steps {
                        echo 'Build Docker'

                        dir('workspace/justdoit_pipe'){
                            script {
                                sh "cp '/var/jenkins_home/workspace/justdoit/justdoit-0.0.1-SNAPSHOT.jar /var/jenkins_home/workspace/justdoit_pipe/'"
                                sh "pwd"
                                dockerImage = docker.build imageName

                            }
                        }
                    }
                }
        /* stage('Login'){
            steps{
                  sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin' // docker hub 로그인
            }
        }
        stage('Deploy our image') {
            steps {
                  sh 'docker push $repository:$BUILD_NUMBER' //docker push
            }
        }
        stage('Cleaning up') {
        	steps {
                  sh "docker rmi $repository:$BUILD_NUMBER" // docker image 제거
            }
        } */
	}
}
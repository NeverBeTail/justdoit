pipeline {

	environment {
            repository = "neverbetail/justdoit" //docker hub id와 repository 이름
            DOCKERHUB_CREDENTIALS = credentials('DockerHub_credentials') // jenkins에 등록해 놓은 docker hub credentials 이름
            dockerImage = ''
    }

    agent any

    stages {
	    stage("CheckSum") {
			steps {
				checkout scm
			}
		}
        stage('Building our image') {
            steps {
                  script {
                        sh "./pipline_build.sh"
                        sh "cp /var/jenkins_home/workspace/justdoit_pipe/build/libs/justdoit-0.0.1-SNAPSHOT.jar /var/jenkins_home/workspace/justdoit_pipe/" // war 파일을 현재 위치로 복사
                        dockerImage = docker.build repository + ":$BUILD_NUMBER"
                  }
            }
        }
         stage('Login'){
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
        }
	}
}
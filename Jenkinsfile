pipeline {

	environment {
            imageName = "neverbetail/justdoit" //docker hub id와 repository 이름
            registryCredential = 'DockerHub_credentials' // jenkins에 등록해 놓은 docker hub credentials 이름
            dockerImage = ''
    }

    agent any

    stages {

        stage('Java-Build'){
             steps {
                 echo 'Build Java'
                 sh './pipline_build.sh '
             }
        }
        stage('Docker-Build'){
             steps {
                 echo 'Build Docker'
                 script {
                    dockerImage = docker.build imageName

                 }
             }
        }
        stage('Docker-Push'){
             steps{
                   echo 'Push Docker'
                   script {
                        docker.withRegistry('', registryCredential){
                        dockerImage.push("1.0")
                        }
                   }
             }
        }
        stage('Container Stop&Remove') {
              steps {
                    sh "docker container stop justdoit" // docker  제거
                    sh "docker container rm justdoit"
                    sh "docker rmi neverbetail/justdoit 1.0"

              }
        }
        stage('EC2-Pull&Deploy'){
              steps {
                    echo 'SSH'

                    sshagent(credentials: ['ec2-ssh']) {
                        sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.124.152.182 "docker pull neverbetail/justdoit:1.0 "'
                        sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.124.152.182 "docker run -d -p 8000:8000 --name justdoit neverbetail/justdoit:1.0"'
                    }
              }
        }
	}
}
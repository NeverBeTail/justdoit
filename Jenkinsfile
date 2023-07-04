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
        stage('Cleaning up') {
        		  steps {
                      sh "docker container prune" // docker image 제거
                  }
              }
        stage('EC2-Pull&Deploy'){
                    steps {
                        echo 'SSH'

                        sshagent(credentials: ['ec2-ssh']) {
                            sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.124.152.182 "ls /home/ec2-user/app/"'
                            sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.124.152.182 "docker pull neverbetail/justdoit:1.0"'
                            sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.124.152.182 "docker run neverbetail/justdoit:1.0"'
                        }
                    }
        }

	}
}
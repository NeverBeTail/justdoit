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
                 sh 'cp /var/jenkins_home/workspace/justdoit_pipe/justdoit-0.0.1-SNAPSHOT.jar /var/jenkins_home/workspace/justdoit_pipe/'
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
        stage('EC2-Pull&Deploy'){
                    steps {
                        echo 'SSH'

                        sshagent(credentials: ['ECGumD208']) {
                            sh 'ssh -o StrictHostKeyChecking=no ubuntu@52.79.219.150 "justdoit"'
                            sh 'ssh -o StrictHostKeyChecking=no ubuntu@52.79.219.150 "docker pull neverbetail/justdoit:1.0"'
                            sh 'ssh -o StrictHostKeyChecking=no ubuntu@52.79.219.150 "docker run neverbetail/justdoit:1.0"'
                        }
                    }
                }
	}
}
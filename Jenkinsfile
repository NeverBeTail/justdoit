
pipeline {
	agent any
	stages {
		stage("CheckSum") {
			steps {
			    echo 'Git clone...'
				checkout scm
			}
		}
		stage("Build") {
        	steps {
        	    echo 'Build...'
                sh './gradlew build'
        	}
        }

	}
}
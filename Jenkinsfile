pipeline {
    agent none
    stages {
        stage('build') {
            agent {
                docker {
                    image 'maven: 3.5.0'
                }
            }
            steps {
                sh 'mvn clean install'
            }
        }
    }
}
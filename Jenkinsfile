pipeline{
    agent any
    stages{
        stage('Build app') {
            steps {
                withMaven (maven: 'MAVEN', jdk: 'JDK1.8.0') {
                    sh 'mvn install -DskipTests=true' 
                }
            }
        }
        stage('Unit tests'){
            steps {
                withMaven (maven: 'MAVEN', jdk: 'JDK1.8.0') {
                    sh 'mvn test' 
                }
            }
        }
    }
}

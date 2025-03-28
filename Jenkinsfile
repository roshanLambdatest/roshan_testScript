pipeline {
    agent any

    environment {
        LT_USERNAME = credentials('roshank')
        LT_ACCESS_KEY = credentials('LT_0uuZ6lYn8sxQ55aGYiXLDG3lwakfEsccxuWBBfm4EeMb4Wm')
    }

    stages {
        stage('Setup') {
            steps {
                git branch: 'main', 
                url: 'https://github.com/roshanLambdatest/roshan_testScript'
            }
        }

        stage('Start LambdaTest Tunnel') {
            steps {
                sh '''
                wget https://downloads.lambdatest.com/tunnel/v3/linux/lt.zip
                unzip lt.zip
                ./LT --user $LT_USERNAME --key $LT_ACCESS_KEY --tunnelName JenkinsTunnel &
                sleep 30
                '''
            }
        }

        stage('Tests') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Stop Tunnel') {
            steps {
                sh 'pkill -f LT'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
        }
    }
}
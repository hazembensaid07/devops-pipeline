def gv
pipeline {
    agent any
    tools {
     maven  'maven'
    }
       environment {
        
        IMAGE_NAME="hazem06/spring-project:${BUILD_NUMBER}"
        NEXUS_VERSION = "nexus"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "http://13.38.62.189/:8081"
        NEXUS_REPOSITORY = "maven-nexus-repo"
        NEXUS_CREDENTIAL_ID = "nexus"
        committerEmail = sh(returnStdout: true, script: 'git log --format="%ae" | head -1').trim()
     
        currentDate = sh(returnStdout: true, script: 'date +%Y-%m-%d-%H-%M').trim()
        
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
     
        stage("get code") {
            steps {
                script {
                   
                 gv.cloneCode()
                }
            }
        }
      
           stage("build image") {
            steps {
                script {
                  
                   gv.buildImage(env.IMAGE_NAME)
                }
            }
        }
        stage("deploy") {
           steps {
             script {
                def dockercmd="docker run --name spring -p 8083:8080 -d ${env.IMAGE_NAME}"
                def dockerdeletecmd="docker stop  spring && docker rm spring"
                sshagent(['ssh']) {
                   sh "ssh -o StrictHostKeyChecking=no ubuntu@13.38.62.189 ${dockerdeletecmd}"
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@13.38.62.189 ${dockercmd}"
                  

                }
              }
           }
        }
    }
     post {
        
       
         success {  
              emailext body: "${committerEmail} has pushed ${GIT_COMMIT} at ${currentDate} with success to CTmiddle production ", to: 'hazembensaid195@gmail.com' , subject: 'Production Start-up '
         }  
         failure {  
            emailext body: "${committerEmail} has pushed ${GIT_COMMIT} at ${currentDate} with fail to CTmiddle production ", to: 'hazembensaid195@gmail.com', subject: 'Production Start-up'
         }  
        }
      
}
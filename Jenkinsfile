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
            script {
                def dockercmd="docker run -p 8083:8080 -d ${imageName}"
                def dockerdeletecmd="docker rm $(docker ps -aq)"
                sshagent(['ssh']) {
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@13.38.62.189 ${dockerdeletecmd}"
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@13.38.62.189 ${dockercmd}"
                  

                }
            }
        }
    }   
}
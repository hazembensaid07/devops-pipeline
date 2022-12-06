def gv
pipeline {
    agent any
    tools {
     maven  'maven'
    }
       environment {
        
        IMAGE_NAME="hazembensaid07/spring-project:${BUILD_NUMBER}"
        
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
        stage("build jar") {
            steps {
                script {
                   
                 gv.buildJar()
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
      
    }   
}
def gv
pipeline {
    agent any
    tools {
     maven  'maven'
    }
       environment {
        
        IMAGE_NAME="hazem06/spring-project:${BUILD_NUMBER}"
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "13.39.51.155:8081"
        NEXUS_REPOSITORY = "devops"
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
       stage("sonar test") {
            steps {
                script {
                   gv.runSonar()
                           }
            }
        }
        stage("Publish to Nexus Repository") {
            steps {
                script {
                    
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
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
              emailext body: "${committerEmail} has pushed ${GIT_COMMIT} at ${currentDate} with success ", to: 'hazembensaid195@gmail.com' , subject: 'Production Start-up '
         }  
         failure {  
            emailext body: "${committerEmail} has pushed ${GIT_COMMIT} at ${currentDate} with fail  ", to: 'hazembensaid195@gmail.com', subject: 'Production Start-up'
         }  
        }
      
}
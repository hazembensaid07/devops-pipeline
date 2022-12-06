def cloneCode() {
   //cloning code 
   git credentialsId: 'github' , url: 'https://github.com/hazembensaid07/spring-crud.git' , branch : 'master'
   cat pom.xml
} 
def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
} 

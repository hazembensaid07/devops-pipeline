def cloneCode() {
   //cloning code 
   sh "git credentialsId: 'github' , url: 'https://github.com/hazembensaid07/spring-crud.git' , branch : 'master'"
   sh "cat pom.xml"
} 
def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
} 

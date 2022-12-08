def cloneCode() {
   //cloning code 
           git credentialsId: 'github', url: 'https://github.com/hazembensaid07/spring-crud-devops.git'
           sh "git checkout master"
} 
def buildJar() {
    echo 'building the application...'
    sh 'cat pom.xml'
} 
def buildImage(String imageName) {
    echo 'building the docker image...'
    //getting credentials of github from jenkins
    withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        //building the image with dockerhub repo tag
        sh " docker build -t $imageName ."
     
        //login to dockerhub
        sh "echo $PASS |  docker login -u $USER --password-stdin"
        //pushing the image to dockerhub
        sh " docker push $imageName"
    
    }
} 
def runSonar (String imageName) {
    sh 'mvn sonar:sonar \
                   -Dsonar.projectKey=devop \
                   -Dsonar.host.url=http://35.181.43.26:9000 \
                   -Dsonar.login=c6b7d8e141bf20018e908762382a4649baac9696' 
}
return this
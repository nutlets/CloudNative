pipeline {
    agent none
    stages {
        stage('Clone Code') {
            agent {
                label 'master'
            }
            steps {
                echo "1.Git Clone Code"
                git url: "https://github.com/nutlets/CloudNative.git"
            }
        }
        stage('Maven Build') {
            agent {
            docker {
                image 'maven:latest'
                args '-v /root/.m2:/root/.m2'
            }
            }
            steps {
                echo "2.Maven Build Stage"
                sh 'mvn -B clean package -Dmaven.test.skip=true'
            }
        }
        stage('Image Build') {
            agent {
                label 'master'
            }
            steps {
                echo "3.Image Build Stage!!!"
                sh 'docker build -f Dockerfile --build-arg jar_name=target/cloud-native-demo-0.0.1-SNAPSHOT.jar -t cloud-native-demo:${BUILD_ID} . '
                echo 'Building Success!'
            }
        }
        stage('Push') {
            agent {
                label 'master'
            }
            steps {
                echo "4.Push Docker Image Stage"
                sh "docker login harbor.edu.cn nju17 nju172022"
                echo 'Log in Success!'
                sh 'docker tag cloud-native-demo:${BUILD_ID} harbor.edu.cn/nju17/cloud-native-demo:${BUILD_ID}'
                sh "docker push harbor.edu.cn/nju17/cloud-native-demo:${BUILD_ID}"
                echo 'Push Success!'
            }
        }
    }

}
node('slave') {
    container('jnlp-kubectl') {
        stage('Clone YAML') {
            echo "5. Git Clone YAML To Slave"
            git url: "https://github.com/nutlets/CloudNative.git"
        }
        stage('YAML') {
                echo "6. Change YAML File Stage"
                sh 'sed -i "s#{VERSION}#${BUILD_ID}#g" ./cloud-native-demo.yaml'
            }
        stage('Deploy') {
            echo "7. Deploy To K8s Stage"
            sh 'kubectl apply -f ./cloud-native-demo.yaml'
        }
    }
}
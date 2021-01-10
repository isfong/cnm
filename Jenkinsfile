pipeline {
    agent {
        kubernetes {
            yaml """
apiVersion: v1
kind: Pod
spec:
  serviceAccountName: deploy-user
  securityContext:
    fsGroup: 1000
  containers:
  - name: maven
    image: maven:3.6.3-jdk-11
    imagePullPolicy: IfNotPresent
    command:
    - sleep
    args:
    - infinity
    resources:
      requests:
        cpu: "0.5"
        memory: 512Mi
      limits:
        cpu: "1"
        memory: 1Gi  
    volumeMounts:
      - name: parent-hosts
        mountPath: "/parent-hosts"
      - name: docker
        mountPath: "/usr/bin/docker"
      - name: dockersock
        mountPath: "/var/run/docker.sock"
      - name: m2
        mountPath: "/root/.m2"
        
#  - name: helm
#    image: alpine/helm:3.3.1
  - name: helmfile
    image: registry.cn-shenzhen.aliyuncs.com/kubernetes-mirror/quay.io-roboll-helmfile:helm3-v0.125.0
    imagePullPolicy: IfNotPresent
    command:
    - sleep
    args:
    - infinity
    resources:
      limits:
        cpu: "0.5"
        memory: 256Mi
    volumeMounts:
      - name: parent-hosts
        mountPath: "/parent-hosts"

  volumes:
    - name: parent-hosts
      hostPath:
        path: /etc/hosts
    - name: docker
      hostPath:
        path: /usr/bin/docker
    - name: dockersock
      hostPath:
        path: /var/run/docker.sock
    - name: m2
      hostPath:
        path: /root/.m2
"""
        }
    }
    stages {
        stage( 'Build' ) {
            steps {
                sh 'mkdir -p ../conf'
                git credentialsId: 'code.aliyun.com.isfong', url: 'https://code.aliyun.com/isfong/bossdream-prod.git'
                sh 'cp -rp bossdream* ../conf/'
                git credentialsId: 'code.aliyun.com.yhhlwkj', url: 'https://code.aliyun.com/yuhaoltd/bossdream.git'
                sh 'cp -rp ../conf/bossdream* ./'
                sh 'cat bossdream-commons/src/main/resources/application-prod.yml'
                container( 'maven' ) {
                    sh 'mappingRegistry=$(cat /parent-hosts | grep registry) && echo "${mappingRegistry}" >> /etc/hosts'
                    sh 'cat /etc/hosts'
                    withDockerRegistry( credentialsId: 'registry.bossdream.com.admin', url: 'https://registry.bossdream.com' ) {
                        sh 'mvn clean package -DskipTests'
                        script {
                            businessImageTag = readFile( "bossdream-business/target/image_tag.txt" )
                            commonsImageTag = readFile( "bossdream-commons/target/image_tag.txt" )
                            iotImageTag = readFile( "bossdream-iot/target/image_tag.txt" )
                            mallImageTag = readFile( "bossdream-mall/target/image_tag.txt" )
                        }
                    }
                }
            }
        }
        stage( 'Deploy' ) {
            environment {
                CONTAINER_REGISTRY = "registry.bossdream.com"
                BUSINESS_VERSION = "${ businessImageTag }"
                COMMONS_VERSION = "${ commonsImageTag }"
                IOT_VERSION = "${ iotImageTag }"
                MALL_VERSION = "${ mallImageTag }"
            }
            steps {
                git credentialsId: 'code.aliyun.com.yhhlwkj', url: 'https://code.aliyun.com/yuhaoltd/bossdream.git'
                container( 'helmfile' ) {
                    sh 'cd k8s/apps && helmfile apply'
                }
            }
        }
    }
}
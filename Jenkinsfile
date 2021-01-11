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
                git credentialsId: 'gitee.com.isfong', url: 'https://gitee.com/isfong/cnm.git'
                container( 'maven' ) {
                    sh 'mappingRegistry=$(cat /parent-hosts | grep registry) && echo "${mappingRegistry}" >> /etc/hosts'
                    sh 'cat /etc/hosts'
                    withDockerRegistry( credentialsId: 'registry.bossdream.com.admin', url: 'https://registry.bossdream.com' ) {
                        sh 'mvn clean package -DskipTests'
                        script {
                            productServiceTag = readFile( "cnm-product-service/target/image_tag.txt" )
                            inventoryServiceTag = readFile( "cnm-inventory-service/target/image_tag.txt" )
                            orderServiceTag = readFile( "cnm-order-service/target/image_tag.txt" )
                        }
                    }
                }
            }
        }
        stage( 'Deploy' ) {
            environment {
                CONTAINER_REGISTRY = "registry.bossdream.com"
                CNM_PRODUCT_SERVICE_VERSION = "${ productServiceTag }"
                CNM_INVENTORY_SERVICE_VERSION = "${ inventoryServiceTag }"
                CNM_ORDER_SERVICE_VERSION = "${ orderServiceTag }"
            }
            steps {
                git credentialsId: 'gitee.com.isfong', url: 'https://gitee.com/isfong/cnm.git'
                container( 'helmfile' ) {
                    sh 'cd _operations/apps && helmfile apply'
                }
            }
        }
    }
}
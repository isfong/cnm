```shell
$ systemctl stop firewalld
```
```shell
$ yum install nfs-utils -y
```
```shell 
$ systemctl enable rpcbind && systemctl enable nfs-server
$ systemctl start rpcbind && systemctl start nfs-server
```
```shell 
$ mkdir -p /nfs-storage
```
```shell
$ vim /etc/exports 
# additional the line:
/nfs-storage *(rw,sync,no_root_squash)
```
```shell
$ systemctl reload nfs
$ showmount -e
```
```shell 
$ mkdir -p /root/nfs-subdir-external-provisioner-helm && cd /root/nfs-subdir-external-provisioner-helm
```
```shell 
$ git clone https://gitee.com/isfong/nfs-subdir-external-provisioner.git
```
```shell 
$ cd nfs-subdir-external-provisioner/deploy/helm
$ kubectl create ns nfs-subdir-external-provisioner-helm
```
```shell 
$ helm install nfs-subdir-external-provisioner . -n nfs-subdir-external-provisioner-helm \
    --set nfs.server=172.17.225.1 \
    --set image.repository=registry.cn-shenzhen.aliyuncs.com/kubernetes-mirror/quay.io-external_storage-nfs-client-provisioner \
    --set image.tag=v3.1.0-k8s1.11
$ kubectl -n nfs-subdir-external-provisioner-helm get pods
NAME                                               READY   STATUS    RESTARTS   AGE
nfs-subdir-external-provisioner-8456cb7558-fqfgh   1/1     Running   0          22s
```
* The default: 
    * `nfs.path=/nfs-storage` The directory corresponding to `nfs-server`
    * `storageClass.name=nfs-client` Need mount PVC to PV Pods `e.g. helm install my-release chart_name --set persistence.storageClass=nfs-client`
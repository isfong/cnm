# Deploy to K8S from 0 to 1

## Log in to any maser
```shell
$ mkdir -p ~/cnm && cd ~/cnm
```
## Copy the ops dir to ~/cnm
* Create the namespace.(The namespace enabled istio sidecar.)
```shell
$ kubectl apply -f ops/namespace.yaml
namespace/cnm created
```
## Install the auto create pv app [nfs-subdir-external-provisioner](https://github.com/kubernetes-sigs/nfs-subdir-external-provisioner)
* Details see `infrastructures/nfs-pvc-auto-pv` dri `README.md`
## Install helm kafka
* Copy the `infrastructures/kafka` to master server '~/cnm/infrastructures/kafka'
```shell
$ pwd
/root/cnm/infrastructures
$ ls
kafka
```
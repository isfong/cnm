# Deploy to K8S from 0 to 1

## Log in to any maser
```shell
$ mkdir -p ~/cnm && cd ~/cnm
```
## Copy the ops dir to ~/cnm
* Create the namespace.(The namespace enabled istio sidecar.)
```shell
$ pwd & ls
/root/cnm
ops
$ kubectl apply -f ops/namespace.yaml
namespace/cnm created
```
## Install the auto create pv app [nfs-subdir-external-provisioner](https://github.com/kubernetes-sigs/nfs-subdir-external-provisioner)
* Details see `infrastructures/nfs-pvc-auto-pv` dri `README.md`
## Install helm kafka
* The app services Event and Command exchange via `Kafka`, `Kafka` dependency on `ZooKeeper`,  `bitnami/kafka` ensures their integration.
* Copy the `infrastructures/kafka` to master server '~/cnm/infrastructures/kafka'
```shell
$ pwd & ls
/root/cnm/infrastructures
kafka
$ cd kafka && helmfile apply # Waiting for installation to complete
$ kubectl -n cnm get pods # READY has 2/2 is the istio injection sidecar by namespace cnm
NAME                READY   STATUS    RESTARTS   AGE
kafka-0             2/2     Running   1          4m37s
kafka-zookeeper-0   2/2     Running   0          4m37s
```
## Install APP services database postgres
* Since it is a separate database for each microservice, it needs to be installed accordingly
* Copy the `infrastructures/postgres` dir to master server `~/cnm/infrastructures/postgres`
``` shell
$ pwd && ls
/root/cnm/infrastructures
kafka  postgres
$ helmfile -f postgres/helmfile.yaml apply # Waiting for installation to complete
$ kubectl -n cnm get pods
NAME                                      READY   STATUS    RESTARTS   AGE
kafka-0                                   2/2     Running   1          19m
kafka-zookeeper-0                         2/2     Running   0          19m
postgres-inventory-service-postgresql-0   2/2     Running   0          95s
postgres-order-service-postgresql-0       2/2     Running   0          95s
postgres-product-service-postgresql-0     2/2     Running   0          94s
```
## Install CDC service
* In addition to a database and message broker, you will need to run the Eventuate Tram CDC service. It reads events inserted into the database and publishes them to the message broker. It is written using Spring Boot.
* Copy the `infrastructures/cdc-service` dir to master server `~/cnm/infrastructures/cdc-service`
``` shell
$ pwd && ls
/root/cnm/infrastructures
cdc-service  kafka  postgres
$ helmfile -f cdc-service/helmfile.yaml apply # Waiting for installation to complete
$ kubectl -n cnm get pods
NAME                                      READY   STATUS    RESTARTS   AGE
cdc-service-7d646b9d66-5g5zx              2/2     Running   0          77s
kafka-0                                   2/2     Running   1          31m
kafka-zookeeper-0                         2/2     Running   0          31m
postgres-inventory-service-postgresql-0   2/2     Running   0          13m
postgres-order-service-postgresql-0       2/2     Running   0          13m
postgres-product-service-postgresql-0     2/2     Running   0          13m
```
## Now the microservices infrastructures install successful.
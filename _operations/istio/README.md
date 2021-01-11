```shell
$ kubectl create secret tls secret-tls-cnm-isfong.com \
    --namespace=cnm \
    --key=isfong.com_tls/isfong.com.key \
    --cert=isfong.com_tls/isfong.com.pem
$ kubectl apply -f gateway.yaml
$ kubectl apply -f virtualservice.yaml
```
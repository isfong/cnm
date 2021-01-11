# Create database secrets with postgresql server
```shell
$ kubectl create secret generic secret-postgres-product-service-password \
    --namespace=cnm \
    --from-literal=postgresql-password=postgres

$ kubectl create secret generic secret-postgres-inventory-service-password \
    --namespace=cnm \
    --from-literal=postgresql-password=postgres

$ kubectl create secret generic secret-postgres-order-service-password \
    --namespace=cnm \
    --from-literal=postgresql-password=postgres
```

# Use with postgresql server
```shell
$ helm install postgres-product-service bitnami/postgresql \
    --version=10.2.1 \
    --set postgresqlDatabase=testdb \
    --set postgresqlUsername=postgres
    --set existingSecret=secret-postgres-product-service-password
```

# Use with app
```yaml
spec:
  template:
    spec:
      containers:
          env:
#            - name: SPRING_DATASOURCE_USERNAME
#              valueFrom:
#                secretKeyRef:
#                  name: secret-postgres-cnm-product-service
#                  key: spring_datasource_username
            - name: SPRING_DATASOURCE_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: secret-postgres-product-service-password
                  key: postgresql-password
```

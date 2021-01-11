# Create database secrets
```shell
kubectl create secret generic secret-postgres-cnm-order-service \
    --namespace=cnm \
    --from-literal=spring_datasource_username=postgres \
    --from-literal=spring_datasource_password=postgres
```
# Use
```yaml
spec:
  template:
    spec:
      containers:
          env:
            - name: SPRING_DATASOURCE_USERNAME
              valueFrom:
                secretKeyRef:
                  name: secret-postgres-cnm-product-service
                  key: spring_datasource_username
            - name: SPRING_DATASOURCE_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: secret-postgres-cnm-product-service
                  key: spring_datasource_username
```

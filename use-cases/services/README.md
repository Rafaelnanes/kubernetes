### Firstly
```
minikube tunnel
```

### Then create the load balancer


### Using ingress
```
curl --resolve "java-app.example.com:80:$( minikube ip )" -i http://java-app.example.com
```
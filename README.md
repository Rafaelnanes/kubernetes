This is the architecture view:
![kubernetes.jpg](images/kubernetes.jpg)

## Create docker image
``` 
gradle bootBuildImage
```

## Kubectl Commands
### Creates deployment
```
kubectl apply -f kubernetes/nodePort/deployment.yaml
```

### Creates service
```
kubectl apply -f kubernetes/nodePort/serviceyaml
```

### Creates deployment and service
``` 
kubectl apply -f kubernetes/nodePort
```


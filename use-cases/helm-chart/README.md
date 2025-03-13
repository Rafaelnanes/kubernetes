## Install a helm
```
helm install my-helm-app ./my-helm-app
helm install my-product-app ./java-app -f product-values.yaml
```

## Modify the params
```
helm install my-helm-app ./my-helm-app --set appName=java-app --set image=java-app:2.0.0
```

## Helm history
```
helm history my-helm-app
```

## Upgrade helm to create new history
```
helm upgrade my-helm-app ./my-helm-app --set image=java-app:2.0.0 --set chart.appVersion=2.0.0 --set chart.version=0.2.0
```


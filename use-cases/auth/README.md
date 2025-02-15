## Creating user
<details>

### Generate private key
```
openssl genrsa -out rafael.key 2048
```
#### generates rafael.key

---
### Create Certificate Signing Request (CSR)
```
openssl req -new -key rafael.key -out rafael.csr -subj "/CN=rafael/O=development"
```

#### generates rafael.csr

---
### Sign the certificate using Kubernetes CA:
```
sudo openssl x509 -req -in rafael.csr \
    -CA /etc/kubernetes/pki/ca.crt \
    -CAkey /etc/kubernetes/pki/ca.key \
    -CAcreateserial \
    -out rafael.crt -days 365
```

#### generates rafael.crt

---

### Change cluster context
```
kubectl config set-credentials rafael --client-certificate=rafael.crt --client-key=rafael.key
kubectl config set-context rafael-context --cluster=your-cluster-name --user=rafael --namespace=my-apps
kubectl config use-context rafael-context
```

### Validate if all the commands worked
```
kubectl config current-context
```

#### And find for "current-context: rafael-context" 
___

</details>

## Creating roles for user

<details>

### Change cluster context
```
config use-context minikube
```
```
kubectl apply -f role.yaml
```

</details>



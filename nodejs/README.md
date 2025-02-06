1 - eval $(minikube docker-env)
2 - Run comand that was told for the command line: minikube -p minikube docker-env --shell powershell | Invoke-Expression
3 - docker build -t nodejs-app:latest .
4 - minikube ssh 'docker image ls'
	- Check if the image nodejs-app is on the image list
5 - kubectl apply -f .\deployment.yaml
6 - kubectl apply -f .\service.yaml	
7 -  minikube service --url nodejs-app-service
	- ATTENTION YOU SHOULD LET THE TERMINAL OPENED otherwise the connection is closed (Only for windows)
	- ex: http://127.0.0.1:63146


Useful commands:

kubectl get deployments
kubectl get pods
kubectl get services

minikube service springboot-app-service

kubectl delete -f deployment.yaml
kubectl delete deployment nginx-ap
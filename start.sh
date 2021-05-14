#!/bin/bash

if ! which minikube > /dev/null; then
   echo -e "Command not found! Please install minikube"
   exit 1
fi

minikube start --vm=true
eval "$(minikube docker-env)"
docker build -t jee-api .
kubectl cluster-info
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.30.0/deploy/static/mandatory.yaml
kubectl apply -f jee-api-deployment-dev.yaml
kubectl apply -f ingress-controller.yaml
kubectl apply -f jee-api-service.yaml

exit 0
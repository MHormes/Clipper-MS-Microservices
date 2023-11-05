#!/bin/bash

# Check if kubectl is installed
if ! command -v kubectl &> /dev/null; then
  echo "kubectl is not installed. Please install it and try again."
  exit 1
fi

# Check if Kubernetes cluster is reachable
if ! kubectl cluster-info &> /dev/null; then
  echo "Kubernetes cluster is not reachable. Please check your configuration."
  exit 1
fi

# Install Nginx Ingress Controller
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.0.0/deploy/static/provider/cloud/deploy.yaml

echo "Nginx Ingress Controller has been installed."

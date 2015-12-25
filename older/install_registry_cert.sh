#!/bin/bash


# simply place the CA certificate at /etc/docker/certs.d/registry.crawford.localnet/ca.crt


export CERT_DIR=/etc/docker/certs.d/registry.crawford.localnet/
sudo mkdir -p $CERT_DIR
sudo curl -o $CERT_DIR/ca.crt http://registry.crawford.localnet:10000/registryCA
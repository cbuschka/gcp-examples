PROJECT_DIR := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
PROJECT := $(shell jq --raw-output .project ${PROJECT_DIR}/../settings.tfvars.json)
PREFIX := $(shell jq --raw-output .prefix ${PROJECT_DIR}/../settings.tfvars.json)
REGION := $(shell jq --raw-output .region ${PROJECT_DIR}/../settings.tfvars.json)
VERSION := $(shell git describe --no-match --always --dirty=-dirty-$(shell date +%s))

deploy_resources:
	@cd ${PROJECT_DIR}/infra/resources && \
	terraform init -upgrade && \
	terraform apply -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

destroy_resources:
	@cd ${PROJECT_DIR}/infra/resources && \
	terraform init -upgrade && \
	terraform destroy -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

deploy_service:	build_service
	@cd ${PROJECT_DIR}/infra/service && \
	terraform init -upgrade && \
	terraform apply -auto-approve -var="service_version=${VERSION}" -var-file="${PROJECT_DIR}/../settings.tfvars.json"

destroy_service:
	@cd ${PROJECT_DIR}/infra/service && \
	terraform init -upgrade && \
	terraform destroy -auto-approve -var="service_version=${VERSION}" -var-file="${PROJECT_DIR}/../settings.tfvars.json"

build_service:
	@cd ${PROJECT_DIR}/service && \
	mvn clean package com.google.cloud.tools:jib-maven-plugin:2.8.0:build -Dimage=${REGION}-docker.pkg.dev/${PROJECT}/${PREFIX}crwcs-docker-registry/service:${VERSION} -Djib.from.image=gcr.io/distroless/java-debian10

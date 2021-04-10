PROJECT_DIR := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
PROJECT := $(shell jq --raw-output .project ${PROJECT_DIR}/../settings.tfvars.json)
PREFIX := $(shell jq --raw-output .prefix ${PROJECT_DIR}/../settings.tfvars.json)
REGION := $(shell jq --raw-output .region ${PROJECT_DIR}/../settings.tfvars.json)
VERSION := $(shell git describe --no-match --always --dirty=-dirty-$(shell date +%s))

deploy_resources:
	@cd ${PROJECT_DIR}/infra/resources && \
	terraform init -upgrade && \
	terraform apply -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

deploy_impersonation:
	@cd ${PROJECT_DIR}/infra/impersonation && \
	terraform init -upgrade && \
	terraform apply -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

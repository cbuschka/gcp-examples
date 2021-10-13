PROJECT_DIR := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
PROJECT := $(shell jq --raw-output .project ${PROJECT_DIR}/../settings.tfvars.json)
PREFIX := $(shell jq --raw-output .prefix ${PROJECT_DIR}/../settings.tfvars.json)
REGION := $(shell jq --raw-output .region ${PROJECT_DIR}/../settings.tfvars.json)

deploy_resources:
	@cd ${PROJECT_DIR}/infra/resources && \
	terraform init -upgrade && \
	terraform apply -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

destroy_resources:
	@cd ${PROJECT_DIR}/infra/resources && \
	terraform init -upgrade && \
	terraform destroy -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

deploy_service:
	@cd ${PROJECT_DIR}/infra/service && \
	terraform init -upgrade && \
	terraform apply -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

destroy_service:
	@cd ${PROJECT_DIR}/infra/service && \
	terraform init -upgrade && \
	terraform destroy -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

open_tunnel:
	@cd ${PROJECT_DIR} && \
	echo "Opening tunnel on localhost:8001..." && \
	gcloud compute ssh ${PREFIX}covm-vm --project ${PROJECT} --zone ${REGION}-a --ssh-flag "-N -L 8001:localhost:80"

connect_via_ssh:
	@cd ${PROJECT_DIR} && \
	gcloud compute ssh ${PREFIX}covm-vm --project ${PROJECT} --zone ${REGION}-a

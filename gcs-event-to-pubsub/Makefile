PROJECT_DIR := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
PREFIX := $(shell jq --raw-output .prefix ${PROJECT_DIR}/../settings.tfvars.json)

deploy_resources:
	@cd ${PROJECT_DIR}/infra/resources && \
	terraform init -upgrade && \
	terraform apply -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"

destroy_resources:
	@cd ${PROJECT_DIR}/infra/resources && \
	terraform init -upgrade && \
	terraform destroy -auto-approve -var-file="${PROJECT_DIR}/../settings.tfvars.json"


upload_file:
	- dd if=/dev/urandom count=1000 of=/dev/stdout | md5sum > temp.txt
	gsutil cp temp.txt gs://${PREFIX}upload-bucket/$(shell uuidgen).txt
	rm temp.txt

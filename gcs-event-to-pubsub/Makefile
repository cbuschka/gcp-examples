PROJECT_DIR := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
GCP_PROJECT = $(shell gcloud config list --format 'value(core.project)' 2>/dev/null)

deploy_resources:
	@cd resources && \
	terraform init -upgrade && \
	terraform apply -auto-approve -var="project=${GCP_PROJECT}"

upload_file:
	- dd if=/dev/urandom count=1000 of=/dev/stdout | md5sum > temp.txt
	gsutil cp temp.txt gs://connis-upload-bucket/$(shell uuidgen).txt
	rm temp.txt

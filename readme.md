# GCP Examples

### Examples on GCP with terraform and Java

## Prerequisites
* bash
* GNU make
* terraform with version .terraform-version or better tfvm (https://github.com/cbuschka/tfvm)
* Java 11
* gcloud sdk 

## Setup

### Configure current project
```
gcloud config set project your-gcp-project-identifier
```

cp settings.tfvars.json.example to settings.tfvars.json and adjust accordingly.

### Login to gcp
```
gcloud auth login
gcloud auth application-default login
gcloud auth configure-docker <your region from settings.tfvars.json>-docker.pkg.dev
```

## Example solutions

| example          | description |
|------------------|-------------|
| [gcs event to pubsub](./gcs-event-to-pubsub) | send notification event via eventarc to pubsub topic, when a file is uploaded into a gcs bucket |
| [pubsub message to cloudrun](./pubsub-to-cloudrun) (ps2cr) | push pubsub messages via subscription to cloudrun with auth enabled |

## Useful links
* [Google Cloud Examples for Java](https://github.com/googleapis/google-cloud-java)

## License
[MIT](./license.txt)

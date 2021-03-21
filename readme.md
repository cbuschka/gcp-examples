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
| [global load balancer to cloudrun](./glb-to-cloudrun) (glb2cr) | global load balancer on port 80 with path mapping to cloud run |
| [gcs access with pure http](./pure-http-gcs) | download file from gcs, login via oauth with serviceaccount key, all pure http without gcp sdk |
| [cloud function from jar](./cloud-function-from-jar) (cffj) | java 11 cloud function deployed as jar, not as source |

## Useful links

* [Google Cloud Examples for Java](https://github.com/googleapis/google-cloud-java)

## License

[MIT](./license.txt)

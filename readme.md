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
| [gcs and pubsub access via pure http](./pure-http-gcp) | download file from gcs, send/receive pubsub messages, login via oauth with serviceaccount key, all pure http without gcp sdk - just an experiment (hint: there is an official [google api client for java](https://developers.google.com/api-client-library/java) with compatibility down to jdk7) |
| [cloud function from jar](./cloud-function-from-jar) (cffj) | java 11 cloud function deployed as jar, not as source |
| [standalone-pubsub-consumer](./standalone-pubsub-consumer) (spsc) | a standalone polling pubsub consumer |
| [serviceaccount-impersonation](./serviceaccount-impersonation) (saimp) | serviceaccount impersonation via terraform |
| [container-on-vm-instance-in-vpc](./container-on-vm-instance-in-vpc) (covm) | gce vm instance in vpc, running docker container with ports bound to host, private ip only, access via iap |

## Useful links

* [Google Cloud Examples for Java](https://github.com/googleapis/google-cloud-java)

## License

[MIT-0](./license.txt)

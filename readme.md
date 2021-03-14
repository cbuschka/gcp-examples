# gcp examples

### gcp, terraform

## prerequisites
* bash
* GNU make
* jq command line tool
* terraform with version .terraform-version or better tfvm (https://github.com/cbuschka/tfvm)

## setup

### login to gcp
```
gcloud login
gcloud auth application-default login
```

### configure current project
```
gcloud config set project your-gcp-project-identifier
```

cp settings.tfvars.json.example to settings.tfvars.json and adjust accordingly.

## examples

| example          | description |
|------------------|-------------|
| [gcs event-to-pubsub](./gcs-event-to-pubsub) | send notification event via eventarc to pubsub topic, when a file is uploaded into a gcs bucket |

## license
[MIT](./license.txt)

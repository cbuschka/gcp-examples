# gcp examples

### gcp, terraform

## prerequisites
* GNU make
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

**All resources will be created in the current project!**

## examples

| example          | description |
|------------------|-------------|
| [gcs event-to-pubsub](./gcs-event-to-pubsub) | send notification event via eventarc to pubsub topic, when a file is uploaded into a gcs bucket |

## license
[MIT](./license.txt)

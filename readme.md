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
[gcs event-to-pubsub](./gcs-event-to-pubsub)

## license
[MIT](./license.txt)

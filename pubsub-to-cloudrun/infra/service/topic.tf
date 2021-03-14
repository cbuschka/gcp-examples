data "google_pubsub_topic" "topic" {
  name = "${var.prefix}ps2cr-topic"
  project = var.project
}

data "google_pubsub_topic" "dead-letter-topic" {
  name = "${var.prefix}ps2cr-dead-letter-topic"
  project = var.project
}

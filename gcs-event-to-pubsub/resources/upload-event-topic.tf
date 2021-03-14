resource "google_pubsub_topic" "upload-event-topic" {
  name = "${var.prefix}upload-event-topic"
  project = var.project
}

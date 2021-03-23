resource "google_pubsub_topic" "topic" {
  name = "${var.prefix}spsc-topic"
  project = var.project
}

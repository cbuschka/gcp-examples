resource "google_pubsub_subscription" "subscription" {
  name = "${var.prefix}spsc-subscription"
  topic = google_pubsub_topic.topic.name
  project = var.project
  message_retention_duration = "1200s"
  retain_acked_messages = false
  ack_deadline_seconds = 20
}

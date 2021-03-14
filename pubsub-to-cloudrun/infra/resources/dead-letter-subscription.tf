resource "google_pubsub_subscription" "dead-letter-subscription" {
  name                       = "${var.prefix}ps2cr-dead-letter-subscription"
  topic                      = google_pubsub_topic.dead-letter-topic.name
  project = var.project
  message_retention_duration = "1200s" # 20 minutes
  retain_acked_messages      = false
  ack_deadline_seconds       = 20
}

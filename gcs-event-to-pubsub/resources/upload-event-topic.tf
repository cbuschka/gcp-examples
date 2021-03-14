resource "google_pubsub_topic" "upload-event-topic" {
  name = "connis-upload-event-topic"
  project = var.project
}

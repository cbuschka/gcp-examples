resource "google_storage_notification" "notification" {
  bucket         = google_storage_bucket.upload-bucket.name
  payload_format = "JSON_API_V1"
  topic          = google_pubsub_topic.upload-event-topic.id
  event_types    = ["OBJECT_FINALIZE", "OBJECT_METADATA_UPDATE"]
  depends_on = [google_pubsub_topic_iam_binding.binding]
}

data "google_storage_project_service_account" "gcs-account" {
  project = var.project
}

resource "google_pubsub_topic_iam_binding" "binding" {
  topic   = google_pubsub_topic.upload-event-topic.id
  role    = "roles/pubsub.publisher"
  members = ["serviceAccount:${data.google_storage_project_service_account.gcs-account.email_address}"]
}

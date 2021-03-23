resource "google_project_service" "pubsub-api" {
  project = var.project
  service = "pubsub.googleapis.com"
}

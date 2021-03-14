resource "google_project_service" "run" {
  project = var.project
  service = "run.googleapis.com"
}

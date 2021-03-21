resource "google_project_service" "run" {
  project = var.project
  service = "cloudfunctions.googleapis.com"
}

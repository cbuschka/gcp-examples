resource "google_artifact_registry_repository" "docker-registry" {
  provider = google-beta
  project = var.project
  location = var.region
  repository_id = "${var.prefix}crwcs-docker-registry"
  description = "docker registry for crwcs"
  format = "DOCKER"
}

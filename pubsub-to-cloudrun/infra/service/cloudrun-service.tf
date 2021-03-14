resource "google_cloud_run_service" "service" {
  name     = "${var.prefix}ps2cr-service"
  location = var.region
  project = var.project
  template {
    spec {
      containers {
        image = "${var.region}-docker.pkg.dev/${var.project}/${var.prefix}ps2cr-docker-registry/service:${var.service_version}"
      }
      service_account_name = data.google_service_account.service-invoker.email
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

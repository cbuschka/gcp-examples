resource "google_cloud_run_service" "service" {
  name     = "${var.prefix}glb2cr-service"
  location = var.region
  project = var.project
  template {
    spec {
      containers {
        image = "${var.region}-docker.pkg.dev/${var.project}/${var.prefix}glb2cr-docker-registry/service:${var.service_version}"
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

data "google_iam_policy" "noauth" {
  binding {
    role = "roles/run.invoker"
    members = [
      "allUsers",
    ]
  }
}

resource "google_cloud_run_service_iam_policy" "noauth" {
  location    = google_cloud_run_service.service.location
  project     = google_cloud_run_service.service.project
  service     = google_cloud_run_service.service.name

  policy_data = data.google_iam_policy.noauth.policy_data
}

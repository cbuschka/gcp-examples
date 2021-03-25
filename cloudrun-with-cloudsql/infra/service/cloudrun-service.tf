data "google_compute_default_service_account" "default" {
  project = var.project
}

resource "google_cloud_run_service" "service" {
  name = "${var.prefix}crwcs-service"
  location = var.region
  project = var.project

  template {
    spec {
      containers {
        image = "${var.region}-docker.pkg.dev/${var.project}/${var.prefix}crwcs-docker-registry/service:${var.service_version}"
        resources {
          limits = {
            cpu: "2"
            memory: "1Gi"
          }
        }

        env {
          name = "CLOUDSQL_INSTANCE_CONNECTION_NAME"
          value = "${var.project}:${var.region}:${data.google_sql_database_instance.crwcs-database-primary.name}"
        }
      }
      service_account_name = data.google_compute_default_service_account.default.email
    }

    metadata {
      annotations = {
        "autoscaling.knative.dev/maxScale" = "2"
        "run.googleapis.com/client-name" = "terraform"
        "run.googleapis.com/vpc-access-egress": "private-ranges-only"
        "run.googleapis.com/vpc-access-connector" = "projects/${var.project}/locations/${var.region}/connectors/${var.prefix}crwcs-cnctr"
        "run.googleapis.com/cloudsql-instances" = "${var.project}:${var.region}:${data.google_sql_database_instance.crwcs-database-primary.name}"
      }
    }
  }


  traffic {
    percent = 100
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
  location = google_cloud_run_service.service.location
  project = google_cloud_run_service.service.project
  service = google_cloud_run_service.service.name

  policy_data = data.google_iam_policy.noauth.policy_data
}

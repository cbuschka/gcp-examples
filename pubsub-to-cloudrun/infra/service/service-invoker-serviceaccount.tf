data "google_service_account" "service-invoker" {
  account_id = "${var.prefix}ps2cr-service-invoker"
  project = var.project
}

resource "google_cloud_run_service_iam_binding" "service-invoker-run-invoker-binding" {
  location = var.region
  project  = var.project
  service  = google_cloud_run_service.service.name
  role     = "roles/run.invoker"
  members = [
    "serviceAccount:${var.prefix}ps2cr-service-invoker@${var.project}.iam.gserviceaccount.com",
  ]
}

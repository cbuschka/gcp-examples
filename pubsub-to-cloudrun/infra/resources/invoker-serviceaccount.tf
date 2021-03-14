resource "google_service_account" "service-invoker" {
  account_id = "${var.prefix}ps2cr-service-invoker"
  project = var.project
}

resource "google_project_iam_member" "service-invoker-permission" {
  member   = "serviceAccount:${google_service_account.service-invoker.email}"
  for_each = toset(["roles/artifactregistry.reader"])
  role     = each.value
  project  = var.project
}


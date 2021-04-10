resource "google_service_account" "serviceaccount" {
  account_id = "${var.prefix}saimp-serviceaccount"
  project = var.project
}

resource "google_project_iam_member" "serviceaccount-permission" {
  member   = "serviceAccount:${google_service_account.serviceaccount.email}"
  for_each = toset([])
  role     = each.value
  project  = var.project
}

resource "google_service_account_iam_member" "service_account_token_creator" {
  service_account_id = google_service_account.serviceaccount.id
  role               = "roles/iam.serviceAccountTokenCreator"
  for_each = toset(var.impersonators) 
  member             = "group:${each.key}"
}

resource "google_service_account_iam_member" "service_account_user" {
  service_account_id = google_service_account.serviceaccount.id
  role               = "roles/iam.serviceAccountUser"
  for_each = toset(var.impersonators) 
  member             = "group:${each.key}"
}

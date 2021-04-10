provider "google" {
 alias  = "impersonated"
 access_token = data.google_service_account_access_token.default.access_token
}

provider "google" {
  alias = "default" # can be omitted
  scopes = [ "https://www.googleapis.com/auth/cloud-platform", "https://www.googleapis.com/auth/userinfo.email", ]
}

data "google_service_account_access_token" "default" {
 provider = google
 target_service_account = "${var.prefix}saimp-serviceaccount@${var.project}.iam.gserviceaccount.com"
 scopes = ["userinfo-email", "cloud-platform"]
 lifetime = "300s"
}

data "google_client_openid_userinfo" "current-user" {
  provider = google.impersonated
}


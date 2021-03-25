provider "google" {
  alias = "default"
  project = var.project
}

provider "google-beta" {
  alias = "google-beta"
  project = var.project
}

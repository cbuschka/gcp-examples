data "google_compute_subnetwork" "regional-subnet" {
  provider = google-beta
  name = "${var.prefix}covm-regional-subnet"
  project = var.project
  region = var.region
}



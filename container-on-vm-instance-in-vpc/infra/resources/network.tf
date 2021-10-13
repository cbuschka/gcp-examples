resource "google_compute_network" "global-network" {
  name = "${var.prefix}covm-global-network"
  project = var.project
  auto_create_subnetworks = false
  routing_mode = "REGIONAL"
}

resource "google_compute_subnetwork" "regional-subnet" {
  provider = google-beta
  name = "${var.prefix}covm-regional-subnet"
  ip_cidr_range = "10.133.0.0/20"
  region = var.region
  role = "ACTIVE"
  network = google_compute_network.global-network.id
  project = var.project
  purpose = "PRIVATE"
}

resource "google_compute_firewall" "ingress-firewall-allow-from-iap" {
  name = "${var.prefix}ingress-firewall-allow-from-iap"
  network = google_compute_network.global-network.name
  project = var.project
  direction = "INGRESS"

  allow {
    protocol = "tcp"
    ports = [
      "22",
      "80"]
  }

  source_ranges = [
    "35.235.240.0/20"]
  target_tags = [
    "httpd"]
}

resource "google_compute_router" "regional-nat-router" {
  project = var.project
  region = var.region
  name = "${var.prefix}covm-regional-nat-router"
  network = google_compute_network.global-network.name
}

resource "google_compute_router_nat" "regional-nat-gateway" {
  project = var.project
  region = var.region
  name = "${var.prefix}covm-regional-nat-gateway"
  router = google_compute_router.regional-nat-router.name
  nat_ip_allocate_option = "AUTO_ONLY"
  source_subnetwork_ip_ranges_to_nat = "ALL_SUBNETWORKS_ALL_IP_RANGES"
}

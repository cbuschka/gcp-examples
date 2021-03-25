resource "google_compute_network" "clwcs-backend-network" {
  name = "${var.prefix}clwcs-backend-network"
  auto_create_subnetworks = false
  routing_mode = "REGIONAL"
  project = var.project
}

resource "google_compute_subnetwork" "clwcs-backend-network-eu-west1" {
  name = "${var.prefix}clwcs-backend-subnetwork"
  ip_cidr_range = "10.2.0.0/16"
  region = var.region
  project = var.project
  network = google_compute_network.clwcs-backend-network.id
  private_ip_google_access = true
}

resource "google_compute_global_address" "private-ip-range" {
  name = "clwcs-backend-network-private-ip-range"
  purpose = "VPC_PEERING"
  address_type = "INTERNAL"
  ip_version = "IPV4"
  prefix_length = 20
  project = var.project
  network = google_compute_network.clwcs-backend-network.self_link
}

resource "google_service_networking_connection" "private-vpc-connection" {
  network = google_compute_network.clwcs-backend-network.self_link
  service = "servicenetworking.googleapis.com"
  reserved_peering_ranges = [
    google_compute_global_address.private-ip-range.name
  ]
}

resource "google_vpc_access_connector" "serverless-connector" {
  name = "${var.prefix}crwcs-cnctr"
  region = var.region
  project = var.project
  ip_cidr_range = "10.3.0.0/28"
  network = google_compute_network.clwcs-backend-network.name
  depends_on = [
    google_project_service.vpcaccess-api]
}

resource "google_compute_region_network_endpoint_group" "service-network-endpoint-group" {
  provider              = google-beta
  name                  = "${var.prefix}glb2cr-service-network-endpoint-group"
  project               = var.project
  network_endpoint_type = "SERVERLESS"
  region                = var.region
  cloud_run {
    service = google_cloud_run_service.service.name
  }
}

resource "google_compute_backend_service" "service-backend-service" {
  name     = "${var.prefix}glb2cr-service"
  project = var.project
  protocol = "HTTP"
  backend {
    group = google_compute_region_network_endpoint_group.service-network-endpoint-group.id
  }
}

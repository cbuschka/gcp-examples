resource "google_compute_target_http_proxy" "http-proxy" {
  project  = var.project
  provider = google-beta
  name     = "http-proxy"
  url_map  = google_compute_url_map.default.id
}

resource "google_compute_global_forwarding_rule" "port80-forwarding_rule" {
  project    = var.project
  name       = "port80-forwardingrule"
  target     = google_compute_target_http_proxy.http-proxy.id
  port_range = "80"
}

resource "google_compute_url_map" "default" {
  name            = "url-map"
  default_service = google_compute_backend_service.default.id
  project = var.project

  host_rule {
    hosts        = ["*"]
    path_matcher = "allpaths"
  }

  path_matcher {
    name            = "allpaths"
    default_service = google_compute_backend_service.default.id

    path_rule {
      paths   = ["/*"]
      service = google_compute_backend_service.default.id
    }
  }
}

resource "google_compute_backend_service" "default" {
  name        = "default-backend-service"
  project = var.project
  port_name   = "http"
  protocol    = "HTTP"
  timeout_sec = 10

  health_checks = [google_compute_http_health_check.default.id]
}

resource "google_compute_http_health_check" "default" {
  name               = "default-http-health-check"
  project = var.project
  request_path       = "/"
  check_interval_sec = 1
  timeout_sec        = 1
}

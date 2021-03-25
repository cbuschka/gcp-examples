resource "google_compute_firewall" "allow-ssh-for-cloudproxy" {
  name = "${var.prefix}crwcs-allow-ssh"
  description = "Allow SSH traffic to any instance tagged with 'ssh-enabled'"
  network = google_compute_network.clwcs-backend-network.name
  direction = "INGRESS"
  project = var.project

  allow {
    protocol = "tcp"
    ports = [
      "22"]
  }

  target_tags = [
    "ssh-enabled"
  ]
}

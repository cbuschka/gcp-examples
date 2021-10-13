data "template_file" "cloud-config" {
  template = file("${path.module}/cloud-config.yaml")
}

resource "google_compute_instance" "vm" {
  project = var.project
  name = "${var.prefix}covm-vm"
  machine_type = "e2-medium"
  zone = "${var.region}-a"
  can_ip_forward = false

  scheduling {
    automatic_restart = true
    on_host_maintenance = "MIGRATE"
  }

  tags = [
    "httpd"]

  boot_disk {
    initialize_params {
      type = "pd-standard"
      image = "cos-cloud/cos-stable-72-11316-171-0"
    }
  }

  allow_stopping_for_update = false

  /*
  scratch_disk {
    interface = "SCSI"
  }
  */

  network_interface {
    subnetwork = data.google_compute_subnetwork.regional-subnet.name
    subnetwork_project = data.google_compute_subnetwork.regional-subnet.project
    /*
    // add public ephermeral ip
    access_config {
    }
    */
  }

  metadata = {
    // ssh-keys = "root:${file("${var.public_key_path}")}"
    google-logging-enabled = "false"
    gce-container-declaration = <<EOB
      spec:
        containers:
          - name: instance-4
            image: 'index.docker.io/nginx:1.12'
            stdin: false
            tty: false
            restartPolicy: Always
    EOB
    google-logging-enabled = "true"
    google-monitoring-enabled = "true"
    metadata_startup_script = "echo hi > /test.txt"
  }

  labels = {
    container-vm = "cos-stable-72-11316-171-0"
  }

  service_account {
    scopes = [
      "cloud-platform",
      "https://www.googleapis.com/auth/devstorage.read_only",
      "https://www.googleapis.com/auth/logging.write",
      "https://www.googleapis.com/auth/monitoring.write",
      "https://www.googleapis.com/auth/servicecontrol",
      "https://www.googleapis.com/auth/service.management.readonly",
      "https://www.googleapis.com/auth/trace.append"]
  }
}

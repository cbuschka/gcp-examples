resource "google_storage_bucket" "upload-bucket" {
  name          = "${var.prefix}upload-bucket"
  project = var.project
  location      = "EU"
  force_destroy = true
  uniform_bucket_level_access = true
  lifecycle_rule {
    condition {
      age = 3
    }
    action {
      type = "Delete"
    }
  }
}

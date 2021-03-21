resource "google_storage_bucket" "code-bucket" {
  name = "${var.prefix}cffj-code-bucket"
  project = var.project
  location = "EU"
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

resource "google_storage_bucket_object" "archive" {
  name = "${var.prefix}cffj-service-bundle.zip"
  bucket = "${var.prefix}cffj-code-bucket"
  source = "../../service/target/service-bundle.zip"
}

resource "google_cloudfunctions_function" "function" {
  name = "${var.prefix}cffj-function"
  description = "A test function"
  runtime = "java11"
  project = var.project
  region = var.region

  available_memory_mb = 256
  max_instances = 10
  timeout = 540
  source_archive_bucket = "${var.prefix}cffj-code-bucket"
  source_archive_object = google_storage_bucket_object.archive.name
  trigger_http = true
  entry_point = "com.github.cbuschka.gcp_examples.cloud_function_from_jar.FunctionMain"
}

resource "google_cloudfunctions_function_iam_member" "invoker" {
  project = google_cloudfunctions_function.function.project
  region = google_cloudfunctions_function.function.region
  cloud_function = google_cloudfunctions_function.function.name

  role = "roles/cloudfunctions.invoker"
  member = "allUsers"
}

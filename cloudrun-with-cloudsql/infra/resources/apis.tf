resource "google_project_service" "servicenetworking-api" {
  project = var.project
  service = "servicenetworking.googleapis.com"
}

resource "google_project_service" "run-api" {
  project = var.project
  service = "run.googleapis.com"
}

resource "google_project_service" "sqladmin-api" {
  project = var.project
  service = "sqladmin.googleapis.com"
}


resource "google_project_service" "vpcaccess-api" {
  project = var.project
  service = "vpcaccess.googleapis.com"
}

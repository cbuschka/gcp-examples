resource "google_sql_database" "crwcs-database" {
  name = "main"
  instance = google_sql_database_instance.crwcs-database-primary.name
  project = var.project
}

resource "google_sql_database_instance" "crwcs-database-primary" {
  name = "${var.prefix}crwcs-db-primary"
  database_version = "POSTGRES_13"
  region = var.region
  project = var.project
  deletion_protection = false

  settings {
    tier = "db-f1-micro"
    availability_type = "ZONAL"
    disk_size = 10

    database_flags {
      name  = "max_connections"
      value = "100"
    }

    ip_configuration {
      ipv4_enabled = false
      require_ssl = true
      private_network = google_compute_network.clwcs-backend-network.id
    }
  }

  depends_on = [
    google_service_networking_connection.private-vpc-connection
  ]
}

resource "google_sql_user" "db-user" {
  name = "crwcs"
  instance = google_sql_database_instance.crwcs-database-primary.name
  password = "asdfasdf"
  project = var.project
}

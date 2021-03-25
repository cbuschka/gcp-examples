data "google_sql_database_instance" "crwcs-database-primary" {
  name = "${var.prefix}crwcs-db-primary"
  project = var.project
}

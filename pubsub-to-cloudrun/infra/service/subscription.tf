resource "google_pubsub_subscription" "subscription" {
  # for more infos visit: https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions
  name                       = "${var.prefix}ps2cr-service-subscription"
  topic                      = data.google_pubsub_topic.topic.name
  project = var.project
  message_retention_duration = "1200s" # 20 minutes
  retain_acked_messages      = true
  ack_deadline_seconds       = 20

  push_config {
    push_endpoint = "${google_cloud_run_service.service.status[0].url}/messages"
    attributes = {
      x-goog-version = "v1"
    }
    # this instructs the subscription to send and authorization header with the given service account as invoker
    oidc_token {
      service_account_email = "${var.prefix}ps2cr-service-invoker@${var.project}.iam.gserviceaccount.com"
    }
  }

  dead_letter_policy {
    dead_letter_topic     = "projects/${var.project}/topics/${var.prefix}ps2cr-dead-letter-topic"
    max_delivery_attempts = 5
  }

  retry_policy {
    minimum_backoff = "5s"
  }

  expiration_policy {
    ttl = "300000.5s"
  }
}

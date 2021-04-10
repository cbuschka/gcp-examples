output "impersonated-serviceaccount-email" {
  value = data.google_client_openid_userinfo.current-user.email
}

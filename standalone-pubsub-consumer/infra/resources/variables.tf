variable "project" {
  type = string
}
variable "prefix" {
  type = string
  default = ""
}
variable "region" {
  type = string
  default = "europe-west3"
}

variable "impersonators" {
  type = list(string)
  default = []
}


provider "aws" {
  region = "us-east-1"
}

module "secrets" {
  source = "./modules/secrets"
  # variables passed here
}

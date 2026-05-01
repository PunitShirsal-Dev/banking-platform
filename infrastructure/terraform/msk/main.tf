provider "aws" {
  region = "us-east-1"
}

module "msk" {
  source = "./modules/msk"
  # variables passed here
}

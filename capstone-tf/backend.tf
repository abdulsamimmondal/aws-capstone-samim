terraform {
  backend "s3" {
    bucket         = "418295691381"
    key            = "prod/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
  }
}

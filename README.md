# Project 0 Part 3 - Ducks Service

This repository contains the Duck Spring Boot backend for Project 0.

## Part 3 Summary

- Dockerized the `ducks-service` backend
- Built the app with Maven
- Created a Docker image using the provided Dockerfile
- Tested the container locally on port 8081
- Added a GitHub Actions workflow to build and publish the image to GHCR
- Deployed the container on Render

## Local Run

1. Build the jar:
   mvn clean package

2. Build the Docker image:
   docker build -t ducks-service .

3. Run the container:
   docker run -d -p 8081:8080 ducks-service

4. Test:
   http://localhost:8081/ducks
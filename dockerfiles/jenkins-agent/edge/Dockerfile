FROM selenium/standalone-edge:latest

USER root

RUN apt-get update && \
    apt-get install -y maven git && \
    rm -rf /var/lib/apt/lists/*

USER seluser
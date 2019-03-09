# Dockerfile for redis server

FROM            docker.io/centos:latest
MAINTAINER      debugroom

RUN yum -y install epel-release
RUN yum install -y redis
RUN sed -i s/bind\ 127\.0\.0\.1/\#bind\ 127\.0\.0\.1/g /etc/redis.conf
RUN sed -i s/protected\-mode\ yes/protected\-mode\ no/g /etc/redis.conf
RUN systemctl enable redis.service

EXPOSE 6379

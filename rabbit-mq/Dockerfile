FROM rabbitmq:3.9-rc-management-alpine
LABEL maintainer=kimchheng email=kimchheng101@gmail.com
ENV RABBITMQ_USER admin
ENV RABBITMQ_PASSWORD 2wsxCDE#
ADD init.sh /init.sh
RUN chmod +x /init.sh
CMD ["/init.sh"]

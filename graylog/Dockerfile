FROM graylog/graylog:4.1.3
LABEL maintainer=kimchheng email=kimchheng101@gmail.com
USER ${GRAYLOG_USER}
ENV ELASTIC_SEARCH_HOST http://elasticsearch:9200
ENV MONGODB_HOST mongo
COPY config/graylog.conf /usr/share/graylog/data/config
COPY init.sh /
RUN chmod +x /init.sh
ENTRYPOINT ["/init.sh"]

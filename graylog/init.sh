#!/bin/sh
# Perfom all the needed preprocessing here...
echo "elasticsearch uri '$ELASTIC_SEARCH_HOST'"
echo "mongodb uri '$MONGODB_HOST'"
sed -ie "s|\$ELASTIC_SEARCH_HOST|$ELASTIC_SEARCH_HOST|g" /usr/share/graylog/data/config/graylog.conf
sed -ie "s|\$MONGODB_HOST|$MONGODB_HOST|g" /usr/share/graylog/data/config/graylog.conf
# Invoke the original entrypoint passing the command and arguments
exec /docker-entrypoint.sh $@

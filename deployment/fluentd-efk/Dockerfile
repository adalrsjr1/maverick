FROM fluent/fluentd:v0.14-onbuild
MAINTAINER Adalberto Junior <adalrsjr1@gmail.com>

USER root

WORKDIR /home/fluent
ENV PATH /home/fluent/.gem/ruby/2.3.0/bin:$PATH

# cutomize following "gem install fluent-plugin-..." line as you wish

RUN apk --no-cache --update add sudo build-base ruby-dev
RUN gem install \
            strptime \
            fluent-plugin-amqp \
            fluent-plugin-elasticsearch && \
    #gem sources --clear-all ; \
    apk del .build-deps ; \
    rm -rf /var/cache/apk/*
#    /home/fluent/.gem/ruby/2.3.0/cache/*.gem

EXPOSE 24224 24220 9880 

COPY fluent.conf /fluentd/etc/

CMD exec fluentd -c /fluentd/etc/$FLUENTD_CONF -p /fluentd/plugins $FLUENTD_OPT

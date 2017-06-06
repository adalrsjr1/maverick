FROM fluent/fluentd:v0.14-latest-onbuild
MAINTAINER Adalberto Junior <adalrsjr1@gmail.com>

WORKDIR /home/fluent
ENV PATH /home/fluent/.gem/ruby/2.3.0/bin:$PATH

# cutomize following "gem install fluent-plugin-..." line as you wish

USER root

RUN apk --no-cache --update add sudo build-base ruby-dev && \
    gem install \
            fluent-plugin-amqp && \
    rm -rf /home/fluent/.gem/ruby/2.3.0/cache/*.gem && gem sources -c && \
    apk del sudo build-base ruby-dev git && rm -rf /var/cache/apk/*

EXPOSE 24224 9880 5557 5558 9220 5160/udp

COPY fluent.conf /fluentd/etc/
CMD exec fluentd -c /fluentd/etc/$FLUENTD_CONF -p /fluentd/plugins $FLUENTD_OPT

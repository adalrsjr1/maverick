### Monitoring the Sock Shop app with Maverick

This is how to deploy Maverick alongside [Sock Shop app][sock-shop]
locally for seeing Maverick in action.

You'll need:

- Docker
- Docker Compose

Start a Docker Swarm cluster

    $ docker swarm init

Start the services

    $ docker-compose -f docker-compose.monitoring.dev.yml -f docker-compose.analyzer.sock-shop.yml up

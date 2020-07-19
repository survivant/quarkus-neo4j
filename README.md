Quarkus guide: https://quarkus.io/guides/neo4j

Prerequisites

To complete this guide, you need:

    JDK 1.8+ installed with JAVA_HOME configured appropriately

    an IDE

    Apache Maven 3.6.2+

    Access to a Neo4j Database

    Optional Docker for your system

Setup Neo4j

The easiest way to start a Neo4j instance is a locally installed Docker environment.

docker run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/secret' neo4j:4.0.0

This starts a Neo4j instance, that publishes its Bolt port on 7687 and a web interface on http://localhost:7474.

Have a look at the download page for other options to get started with the product itself.

#
# docker build -f src/main/docker/Dockerfile.jvm -t quarkus/neo4j-quickstart-jvm .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/neo4j-quickstart-jvm
#
###
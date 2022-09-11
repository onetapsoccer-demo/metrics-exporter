#   Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
```shell script
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/metrics-exporter-jvm .
```

```shell script
docker tag quarkus/metrics-exporter-jvm:latest viniciusfcf/one-tap-soccer-metrics-exporter:latest
```

```shell script
docker push viniciusfcf/one-tap-soccer-metrics-exporter:latest
```

or

```shell script
docker tag quarkus/metrics-exporter-jvm:latest quay.io/vflorent/one-tap-soccer-metrics-exporter:latest
```

```shell script
docker push quay.io/vflorent/one-tap-soccer-metrics-exporter:latest
```

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/ -1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


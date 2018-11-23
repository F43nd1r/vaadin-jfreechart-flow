# JFreeChart Flow

Vaadin 10 Java integration for JFreeChart

## Development instructions

Starting the test/demo server, run command (or same via IDE):
```
mvn jetty:run
```

This deploys demo at http://localhost:8080 . Hitting enter in console should reload the context (changes) if you are using the project in your IDE (build also if using IntelliJ)

## Cutting a release

The project has release plugin configured so that if all changes are commited, a new release can be made as follows:

```
mvn release:prepare release:perform
```

target/checkout/target should after that contain a zip file that can be uploaded to Vaadin Directory.


[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/jfreechart-flow)
[![Stars on vaadin.com/directory](https://img.shields.io/vaadin-directory/star/jfreechart-flow.svg)](https://vaadin.com/directory/component/jfreechart-flow)
[![Maven Central](https://img.shields.io/maven-central/v/com.faendir.vaadin/jfreechart-flow.svg)](https://search.maven.org/search?q=com.faendir.vaadin)
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


# Json support for the JDK Http Server

[![javadoc](https://javadoc.io/badge2/dev.mccue/jdk-httpserver-json/javadoc.svg)](https://javadoc.io/doc/dev.mccue/jdk-httpserver-json)
[![Tests](https://github.com/bowbahdoe/jdk-httpserver-json/actions/workflows/test.yml/badge.svg)](https://github.com/bowbahdoe/jdk-httpserver-json/actions/workflows/test.yml)

Utilities for sending JSON responses with the JDK's [built-in HTTP server](https://docs.oracle.com/en/java/javase/21/docs/api/jdk.httpserver/module-summary.html).

Requires Java 21+

## Dependency Information

### Maven

```xml
<dependency>
    <groupId>dev.mccue</groupId>
    <artifactId>jdk-httpserver-json</artifactId>
    <version>2024.12.04</version>
</dependency>
```

### Gradle

```
dependencies {
    implementation("dev.mccue:jdk-httpserver-json:2024.12.04")
}
```


## Usage

```java
import com.sun.net.httpserver.HttpServer;
import dev.mccue.jdk.httpserver.HttpExchangeUtils;
import dev.mccue.jdk.httpserver.json.JsonBody;
import dev.mccue.json.Json;

import java.io.IOException;
import java.net.InetSocketAddress;

void main() throws IOException {
    var server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/", exchange -> {
        HttpExchanges.sendResponse(exchange, 200, JsonBody.of(
                Json.objectBuilder()
                        .put("Hello", "world")
        ));
    });
    server.start();
}
```

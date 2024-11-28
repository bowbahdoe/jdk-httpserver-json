package dev.mccue.jdk.httpserver.json;

import com.sun.net.httpserver.HttpExchange;
import dev.mccue.jdk.httpserver.Body;
import dev.mccue.jdk.httpserver.ResponseLength;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import dev.mccue.json.JsonEncodable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * An implementation of {@link Body} which will respond with {@link Json}.
 */
public final class JsonBody implements Body {
    private final Json json;
    private final byte[] jsonBytes;

    private JsonBody(Json json) {
        this.json = json;
        this.jsonBytes = Json.write(json).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Creates a {@link JsonBody}.
     *
     * @param jsonEncodable The JSON to use.
     * @return A {@link JsonBody}
     */
    public static JsonBody of(JsonEncodable jsonEncodable) {
        Objects.requireNonNull(jsonEncodable);
        return new JsonBody(jsonEncodable.toJson());
    }

    public static Json read(HttpExchange exchange) throws IOException {
        return Json.read(new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        ));
    }

    public static <T> T read(HttpExchange exchange, JsonDecoder<T> decoder) throws IOException {
        return decoder.decode(read(exchange));
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(jsonBytes);
    }

    @Override
    public ResponseLength responseLength() {
        return ResponseLength.known(jsonBytes.length);
    }

    @Override
    public Optional<String> defaultContentType() {
        return Optional.of("application/json; charset=utf-8");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonBody other && json.equals(other.json);
    }

    @Override
    public int hashCode() {
        return json.hashCode();
    }

    @Override
    public String toString() {
        return "JsonBody[" + json + "]";
    }
}

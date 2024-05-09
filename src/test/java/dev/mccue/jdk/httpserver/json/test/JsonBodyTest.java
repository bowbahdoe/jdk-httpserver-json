package dev.mccue.jdk.httpserver.json.test;

import dev.mccue.jdk.httpserver.ResponseLength;
import dev.mccue.jdk.httpserver.json.JsonBody;
import dev.mccue.json.Json;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonBodyTest {
    @Test
    public void testContentType() throws IOException {
        assertEquals(
                Optional.of("application/json; charset=utf-8"),
                JsonBody.of(Json.ofNull()).defaultContentType()
        );
    }

    @Test
    public void testNull() throws IOException {
        var baos = new ByteArrayOutputStream();
        JsonBody.of(Json.ofNull()).writeTo(baos);

        assertArrayEquals("null".getBytes(), baos.toByteArray());
    }

    @Test
    public void testString() throws IOException {
        var baos = new ByteArrayOutputStream();
        JsonBody.of(Json.of("Hello")).writeTo(baos);

        assertArrayEquals("\"Hello\"".getBytes(), baos.toByteArray());
    }

    @Test
    public void testComplex() throws IOException {
        var json = Json.objectBuilder()
                .put("a", Json.arrayBuilder()
                        .add("b")
                        .add(false))
                .put("b", Json.ofNull())
                .put("\uD83D\uDE0A", "\uD83D\uDE0A")
                .build();

        var body = JsonBody.of(json);


        var baos = new ByteArrayOutputStream();
        body.writeTo(baos);

        assertEquals(body.responseLength(), ResponseLength.known(baos.toByteArray().length));
        assertArrayEquals(
                Json.writeString(json).getBytes(StandardCharsets.UTF_8),
                baos.toByteArray()
        );
    }
}

module dev.mccue.jdk.httpserver.json {
    requires transitive dev.mccue.json;
    requires transitive dev.mccue.jdk.httpserver;
    requires jdk.httpserver;

    exports dev.mccue.jdk.httpserver.json;
}
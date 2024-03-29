const { IOException } = require("java.io");
const { Set } = require("java.util");public interface SourceSet {
    Set<PathSource> collect() throws IOException;

    String findExtension();
}
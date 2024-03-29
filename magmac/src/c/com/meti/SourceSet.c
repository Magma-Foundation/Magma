public interface SourceSet {
    Set<PathSource> collect() throws IOException;

    String findExtension();
}
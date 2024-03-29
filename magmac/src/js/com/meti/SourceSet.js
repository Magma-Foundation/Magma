import { IOException } from java.ioimport { Set } from java.utilpublic interface SourceSet {
    Set<PathSource> collect() throws IOException;

    String findExtension();
}
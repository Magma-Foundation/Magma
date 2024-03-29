#include "SourceSet.c"from java.iofrom java.utilpublic interface SourceSet {
    Set<PathSource> collect() throws IOException;

    String findExtension();
}
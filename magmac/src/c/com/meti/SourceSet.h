#ifndef SourceSet_H
#define SourceSet_H
#include <java.io.h>
#include <java.util.h>public interface SourceSet {
    Set<PathSource> collect() throws IOException;

    String findExtension();
}
#endif
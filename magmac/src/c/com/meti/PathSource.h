#ifndef PathSource_H
#define PathSource_H
#include <java.io.h>
#include <java.nio.file.h>
#include <java.nio.file.h>
#include <java.util.h>
#include <java.util.h>
#include <java.util.h>public record PathSource(Path root, Path path) {
    public List<String> findPackage() {
        var list = new ArrayList<String>();
        var parent = root.toAbsolutePath().relativize(path.toAbsolutePath()).getParent();
        if(parent == null) return Collections.emptyList();

        for (int i = 0; i < parent.getNameCount(); i++) {
            list.add(parent.getName(i).toString());
        }
        return list;
    }

    public String findName() {
        var name = path.getName(path.getNameCount() - 1).toString();
        var separator = name.indexOf('.');
        return name.substring(0, separator);
    }

    public String read() throws IOException {
        return Files.readString(path);
    }
}
#endif
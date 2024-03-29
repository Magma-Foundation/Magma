
const { IOException } = require("java.io");
const { Files } = require("java.nio.file");
const { Path } = require("java.nio.file");
const { ArrayList } = require("java.util");
const { Collections } = require("java.util");
const { List } = require("java.util");public record PathSource(Path root, Path path) {
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
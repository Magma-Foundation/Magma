package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public record Application(Set<Path> sources) {
    static Path resolveFile(String name, String extension) {
        return Paths.get(".", name + "." + extension);
    }

    ArrayList<Path> run() throws IOException {
        var list = new ArrayList<Path>();
        for (var path : sources) {
            if (Files.exists(path)) {
                var input = Files.readString(path);

                var root = new CompoundImport();
                var lines = input.split(";");
                for (String line : lines) {
                    var trimmed = line.strip();
                    if (trimmed.startsWith("import ")) {
                        var name = trimmed.substring("import ".length());

                        var args = Arrays.asList(name.split("\\."));
                        var argsCopy = new ArrayList<String>();
                        if (args.size() == 1) argsCopy.add("*");
                        argsCopy.addAll(args);

                        root = root.merge(CompoundImport.from(argsCopy));
                    }
                }

                String output;
                if (!root.isEmpty()) {
                    var rendered = root.render(0);
                    var formatted = rendered.substring(1, rendered.length() - 1).strip();
                    output = "import " + formatted + ";";
                } else {
                    output = "";
                }

                var fileName = path.getFileName().toString();
                var separator = fileName.indexOf(".");
                var fileNameWithoutExtension = fileName.substring(0, separator);

                var target = resolveFile(fileNameWithoutExtension, "mgs");
                Files.writeString(target, output);
                list.add(target);
            }
        }

        return list;
    }

    interface Import {

        Collection<String> collectKeys();

        Optional<List<Import>> apply(String s);

        String render(int depth);
    }

    record SingleImport(String value) implements Import {
        @Override
        public Collection<String> collectKeys() {
            return Collections.emptySet();
        }

        @Override
        public Optional<List<Import>> apply(String s) {
            return Optional.empty();
        }

        @Override
        public String render(int depth) {
            return value;
        }
    }

    record CompoundImport(Map<String, List<Import>> values) implements Import {
        public CompoundImport() {
            this(new HashMap<>());
        }

        public static Import from(List<String> args) {
            if (args.isEmpty()) {
                throw new IllegalArgumentException("Args cannot be empty.");
            }

            var first = args.get(0);
            if (args.size() == 1) {
                return new SingleImport(first);
            }

            var subList = args.subList(1, args.size());
            var child = from(subList);
            return new CompoundImport(Map.of(first, Collections.singletonList(child)));
        }

        public CompoundImport merge(Import other) {
            var set = new HashSet<>(values.keySet());
            set.addAll(other.collectKeys());

            var map = new HashMap<String, List<Import>>();
            for (String s : set) {
                var thisList = values.getOrDefault(s, new ArrayList<>());
                var otherList = other.apply(s).orElse(new ArrayList<>());
                var copy = new ArrayList<>(thisList);
                copy.addAll(otherList);

                var allKeys = copy.stream().map(Import::collectKeys).flatMap(Collection::stream).collect(Collectors.toSet());
                var list = allKeys.stream().collect(Collectors.toMap(s12 -> s12, s1 -> copy.stream().map(entry -> {
                            return entry.apply(s1);
                        })
                        .flatMap(Optional::stream)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())));

                copy.removeIf(item -> item instanceof CompoundImport);
                copy.add(new CompoundImport(list));

                map.put(s, copy);
            }

            return new CompoundImport(map);
        }

        @Override
        public Collection<String> collectKeys() {
            return values.keySet();
        }

        @Override
        public Optional<List<Import>> apply(String s) {
            if (values.containsKey(s)) {
                return Optional.of(values.get(s));
            } else {
                return Optional.empty();
            }
        }

        @Override
        public String render(int depth) {
            return values.entrySet().stream()
                    .map(entry -> {
                        var list = entry.getValue();
                        if (list.isEmpty()) {
                            return entry.getKey();
                        } else {
                            String collect;
                            if (list.size() == 1) {
                                collect = list.get(0).render(depth + 1);
                            } else {
                                collect = entry.getValue().stream().map(anImport -> anImport.render(depth + 1))
                                        .collect(Collectors.joining(",", "{", "}"));
                            }
                            return collect + " from " + entry.getKey();
                        }
                    })
                    .map(value -> value)
                    .collect(Collectors.joining(",", "{", " }"));
        }

        public boolean isEmpty() {
            return values.isEmpty();
        }
    }
}
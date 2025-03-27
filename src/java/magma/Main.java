package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path sourceDirectory = Paths.get(".", "src", "java");
        try (Stream<Path> stream = Files.walk(sourceDirectory)) {
            Set<Path> sources = stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toSet());

            for (Path source : sources) {
                String input = Files.readString(source);

                ArrayList<String> segments = new ArrayList<>();
                StringBuilder buffer = new StringBuilder();
                int depth = 0;
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    buffer.append(c);
                    if (c == ';' && depth == 0) {
                        segments.add(buffer.toString());
                        buffer = new StringBuilder();
                    } else {
                        if (c == '{') depth++;
                        if (c == '}') depth--;
                    }
                }
                segments.add(buffer.toString());

                StringBuilder output = new StringBuilder();
                for (String segment : segments) {
                    output.append(compileRootSegment(segment));
                }

                Path relative = sourceDirectory.relativize(source);
                Path parent = relative.getParent();
                Path targetDirectory = Paths.get(".", "src", "windows");
                Path targetParent = targetDirectory.resolve(parent);
                if (!Files.exists(targetParent)) Files.createDirectories(targetParent);

                String nameWithExt = relative.getFileName().toString();
                String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
                Path target = targetParent.resolve(name + ".c");

                Files.writeString(target, output.toString());
            }
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compileRootSegment(String segment) throws CompileException {
        if (segment.startsWith("package ")) return "";
        if (segment.strip().startsWith("import ")) return "#include <temp.h>\n";
        if (segment.contains("class ")) return "struct Temp {\n};\n";
        throw new CompileException("Invalid root segment", segment);
    }
}

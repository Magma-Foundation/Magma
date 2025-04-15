package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            Path target = Paths.get(".", "src", "java", "magma", "main.c");
            Files.writeString(target, "int main(){\n\treturn 0;\n}");

            Process process = new ProcessBuilder("cmd.exe", "/d", "build.bat")
                    .directory(Paths.get(".").toFile())
                    .inheritIO()
                    .start();

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}

/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java"); */
/* String input = Files.readString(source); */
/* Path output = source.resolveSibling("main.c"); */
/* Files.writeString(output, compile(input)); */
/* new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .inheritIO()
                    .start()
                    .waitFor(); */
/* } catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace(); */
/* }
    }

    private static String compile(String input) {
        return getString(input) + "int main(){\n\treturn 0; */
/* \n}\n"; */
/* }

    private static String getString(String input) {
        ArrayList<String> segments = new ArrayList<>(); */
/* StringBuilder buffer = new StringBuilder(); */
/* for (int i = 0; */
/* i < input.length(); */
/* i++) {
            char c = input.charAt(i); */
/* buffer = buffer.append(c); */
/* if (c == '; */
/* ') {
                ArrayList<String> copy = new ArrayList<>(segments); */
/* copy.add(buffer.toString()); */
/* segments = copy; */
/* buffer = new StringBuilder(); */
/* }
        }

        StringBuilder output = new StringBuilder(); */
/* for (String segment : segments) {
            output.append(compileRootSegment(segment)); */
/* }

        return output.toString(); */
/* }

    private static String compileRootSegment(String input) {
        String stripped = input.strip(); */
/* if (stripped.startsWith("package ")) {
            return ""; */
/* }
        return generatePlaceholder(stripped) + "\n"; */
/* }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("<comment-start>", "<comment-start>")
                .replace("<comment-end>", "<comment-end>"); */
/* return "<comment-start> " + replaced + " <comment-end>"; */
int main(){
	return 0;
}

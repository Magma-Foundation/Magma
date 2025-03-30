package magma.compile.rule.divide;

public interface Folder {
    DividingState fold(DividingState current, char c);

    String join(String current, String element);
}

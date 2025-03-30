package magma.compile.rule.divide;

public record DecoratedFolder(Folder folder) implements Folder {
    @Override
    public DividingState fold(DividingState current, char c) {
        return null;
    }

    @Override
    public String join(String current, String element) {
        return folder.join(current, element);
    }
}

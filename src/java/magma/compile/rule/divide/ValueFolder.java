package magma.compile.rule.divide;

public class ValueFolder implements Folder {
    @Override
    public DividingState fold(DividingState current, char c) {
        if (c == ',' && current.isLevel()) return current.advance();

        DividingState appended = current.append(c);
        if (c == '<') return appended.enter();
        if (c == '>') return appended.exit();
        return appended;
    }

    @Override
    public String join(String current, String element) {
        if (current.isEmpty()) return element;
        return current + ", " + element;
    }
}

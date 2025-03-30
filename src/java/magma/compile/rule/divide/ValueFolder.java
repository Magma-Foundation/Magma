package magma.compile.rule.divide;

public class ValueFolder implements Folder {
    @Override
    public DividingState fold(DividingState current, char c) {
        if(c == ',') return current.advance();
        return current.append(c);
    }

    @Override
    public String join(String current, String element) {
        return current + ", " + element;
    }
}

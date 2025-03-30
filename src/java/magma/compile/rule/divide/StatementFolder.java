package magma.compile.rule.divide;

public class StatementFolder implements Folder {
    @Override
    public DividingState fold(DividingState current, char c) {
        DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '}' && appended.isShallow()) return appended.advance().exit();
        if (c == '{' || c == '(') return appended.enter();
        if (c == '}' || c == ')') return appended.exit();
        return appended;
    }

    @Override
    public String join(String current, String element) {
        return current + element;
    }
}
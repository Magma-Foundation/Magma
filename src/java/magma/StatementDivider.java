package magma;

public class StatementDivider implements Divider {
    @Override
    public State apply(State current, char next) {
        final var appended = current.append(next);
        if (next == ';' && appended.isLevel()) return appended.advance();
        if (next == '}' && appended.isShallow()) return appended.advance().exit();
        if (next == '{' || next == '(') return appended.enter();
        if (next == '}' || next == ')') return appended.exit();
        return appended;
    }
}
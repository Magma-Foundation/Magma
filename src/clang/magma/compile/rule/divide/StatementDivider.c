package magma.compile.rule.divide;class package magma.compile.rule.divide;{package magma.compile.rule.divide;

import magma.collect.list.List_;class import magma.collect.list.List_;{

import magma.collect.list.List_;

public class StatementDivider implements Divider {
    @Override
    public List_<String> divide(String input) {
        DividingState current = new MutableDividingState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return current.advance().segments();
    }

    @Override
    public String join(String current, String element) {
        return current + element;
    }

    public static DividingState divideStatementChar(DividingState current, char c) {
        DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }
}class public class StatementDivider implements Divider {
    @Override
    public List_<String> divide(String input) {
        DividingState current = new MutableDividingState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return current.advance().segments();
    }

    @Override
    public String join(String current, String element) {
        return current + element;
    }

    public static DividingState divideStatementChar(DividingState current, char c) {
        DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }
}{

public class StatementDivider implements Divider {
    @Override
    public List_<String> divide(String input) {
        DividingState current = new MutableDividingState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return current.advance().segments();
    }

    @Override
    public String join(String current, String element) {
        return current + element;
    }

    public static DividingState divideStatementChar(DividingState current, char c) {
        DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }
}
package magma.compile.rule.divide;

import magma.collect.list.List_;

public interface Divider {
    List_<String> divide(String input);

    String join(String current, String element);
}

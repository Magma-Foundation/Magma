package magmac.app.compile.rule.filter;

public interface Filter {
    boolean test(String input);

    String createMessage();
}

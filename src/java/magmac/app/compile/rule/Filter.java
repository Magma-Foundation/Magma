package magmac.app.compile.rule;

public interface Filter {
    boolean test(String input);

    String createMessage();
}

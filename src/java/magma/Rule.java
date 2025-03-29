package magma;

import magma.option.Option;

public interface Rule {
    Option<String> compile(String input);
}

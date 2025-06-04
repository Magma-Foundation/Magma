package magmac.app.compile.rule;

import magmac.api.Option;
import magmac.api.Tuple2;

/**
 * Divides an input string into two parts based on some criteria.
 */
public interface Splitter {
    Option<Tuple2<String, String>> split(String input);

    String createMessage();

    String merge(String leftString, String rightString);
}

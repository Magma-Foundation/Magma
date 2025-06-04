package magmac.app.compile.rule.fold;

import magmac.app.compile.rule.divide.DivideState;

/**
 * Consumes characters one by one to build a {@link DivideState}.
 */
public interface Folder {
    DivideState fold(DivideState state, char c);

    String createDelimiter();
}

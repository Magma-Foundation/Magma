package magmac.app.compile.rule.fold;

import magmac.app.compile.rule.divide.DivideState;

public interface Folder {
    DivideState fold(DivideState state, char c);
}

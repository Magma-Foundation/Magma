package magmac.app.compile.rule.fold;

import magmac.app.compile.rule.divide.DivideState;

public class DelimitedFolder implements Folder {
    private final char delimter;

    public DelimitedFolder(char delimter) {
        this.delimter = delimter;
    }

    @Override
    public DivideState fold(DivideState state, char c) {
        if (this.delimter == c) {
            return state.advance();
        }
        return state.append(c);
    }

    @Override
    public String createDelimiter() {
        return String.valueOf(this.delimter);
    }
}

package magmac.app.compile.rule.divide;

import magmac.api.iter.Iter;
import magmac.app.compile.rule.fold.Folder;

public record FoldingDivider(Folder folder) implements Divider {
    @Override
    public Iter<String> divide(String input) {
        DivideState current = new MutableDivideState(input);
        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            current = maybePopped.orElse(null).left();
            char c = maybePopped.orElse(null).right();

            current = this.folder.fold(current, c);
        }

        return current.advance().iter();
    }

    @Override
    public String createDelimiter() {
        return this.folder.createDelimiter();
    }
}
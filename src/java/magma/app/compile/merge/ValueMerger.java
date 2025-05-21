package magma.app.compile.merge;

import magma.api.text.Strings;

public class ValueMerger implements Merger {
    @Override
    public String merge(String cache, String element) {
        if (Strings.isEmpty(cache)) {
            return cache + element;
        }
        return cache + ", " + element;
    }
}
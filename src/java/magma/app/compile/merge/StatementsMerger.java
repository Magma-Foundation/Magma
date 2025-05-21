package magma.app.compile.merge;

public class StatementsMerger implements Merger {
    @Override
    public String merge(String cache, String element) {
        return cache + element;
    }
}

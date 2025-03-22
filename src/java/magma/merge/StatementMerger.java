package magma.merge;

public class StatementMerger implements Merger {
    @Override
    public StringBuilder merge(StringBuilder buffer, String element) {
        return buffer.append(element);
    }
}
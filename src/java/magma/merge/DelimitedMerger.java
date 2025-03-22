package magma.merge;

public record DelimitedMerger(String delimiter) implements Merger {
    @Override
    public StringBuilder merge(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(delimiter()).append(element);
    }
}
package magma.compile.rule.divide;

import magma.collect.list.List_;

public class FoldingDivider implements Divider {
    private final Folder folder;

    public FoldingDivider(Folder folder) {
        this.folder = folder;
    }

    @Override
    public List_<String> divide(String input) {
        DividingState current = new MutableDividingState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = folder.fold(current, c);
        }

        return current.advance().segments();
    }

    @Override
    public String join(String current, String element) {
        return folder.join(current, element);
    }
}
package magma.app.compile.split;

import magma.api.Tuple2;
import magma.api.collect.list.ListCollector;
import magma.api.option.None;
import magma.api.option.Option;
import magma.app.compile.divide.FoldedDivider;
import magma.app.compile.fold.DecoratedFolder;
import magma.app.compile.fold.Folder;
import magma.app.compile.select.Selector;

public record FoldingSplitter(Folder folder, Selector selector) implements Splitter {
    @Override
    public Option<Tuple2<String, String>> apply(String input) {
        var divisions = new FoldedDivider(new DecoratedFolder(this.folder)).divide(input)
                .collect(new ListCollector<String>());

        if (2 > divisions.size()) {
            return new None<Tuple2<String, String>>();
        }

        return this.selector.select(divisions);
    }
}

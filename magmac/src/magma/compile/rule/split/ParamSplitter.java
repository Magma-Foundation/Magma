package magma.compile.rule.split;

import java.util.List;

public class ParamSplitter implements Splitter {
    @Override
    public List<String> split(String input) {
        return List.of(input.split(","));
    }
}

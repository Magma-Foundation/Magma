package magma.compile.rule.split;

import java.util.Arrays;
import java.util.List;

public class ParamSplitter implements Splitter {
    @Override
    public List<String> split(String input) {
        return Arrays.stream(input.split(","))
                .filter(value -> !value.isBlank())
                .toList();
    }
}

package magma.compile.rule.split;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParamSplitter implements Splitter {
    @Override
    public List<String> split(String input) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);

            if (c == ',' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
                buffer.append(c);
            }
        }

        segments.add(buffer.toString());

        return segments.stream()
                .filter(value -> !value.isBlank())
                .toList();
    }
}

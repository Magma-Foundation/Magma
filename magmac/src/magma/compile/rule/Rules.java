package magma.compile.rule;

import magma.api.Tuple;

import java.util.Optional;

public class Rules {
    static Optional<Integer> wrapIndex(int index) {
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    static Optional<Tuple<String, String>> splitAtSlice(String input, String slice) {
        return wrapIndex(input.indexOf(slice)).map(keywordIndex -> {
            var left = input.substring(0, keywordIndex);
            var right = input.substring(keywordIndex + slice.length());
            return new Tuple<>(left, right);
        });
    }
}

package magma.compile.lang;

import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.collect.stream.head.HeadedStream;
import magma.collect.stream.head.RangeHead;
import magma.compile.rule.locate.Locator;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;

public class InvocationStartLocator implements Locator {

    public static final Tuple<Integer, Character> DEFAULT_PAIR = new Tuple<>(0, '\0');

    private static List_<Tuple<Integer, Character>> skipDoubleQuotes(List_<Tuple<Integer, Character>> queue) {
        List_<Tuple<Integer, Character>> current = queue;
        while (true) {
            Tuple<Boolean, Tuple<Tuple<Integer, Character>, List_<Tuple<Integer, Character>>>> first = current.popFirst()
                    .toTuple(new Tuple<>(DEFAULT_PAIR, current));

            if (first.left()) {
                Tuple<Integer, Character> pair = first.right().left();
                current = first.right().right();

                Tuple<Integer, Character> tuple = current.findFirst().orElse(DEFAULT_PAIR);
                if (tuple.right() == '\\') {
                    current = current.popFirst().orElse(new Tuple<>(DEFAULT_PAIR, current)).right();
                } else if (pair.right() == '"') break;
            } else {
                break;
            }
        }

        return current;
    }

    @Override
    public Option<Integer> locate(String input, String infix) {
        List_<Tuple<Integer, Character>> queue = new HeadedStream<>(new RangeHead(input.length()))
                .map(index -> input.length() - index - 1)
                .map(index -> new Tuple<>(index, input.charAt(index)))
                .collect(new ListCollector<>());

        int depth = 0;
        while (true) {
            Tuple<Boolean, Tuple<Tuple<Integer, Character>, List_<Tuple<Integer, Character>>>> maybeNext = queue.popFirst()
                    .toTuple(new Tuple<>(DEFAULT_PAIR, queue));

            if (maybeNext.left()) {
                Tuple<Integer, Character> pair = maybeNext.right().left();
                queue = maybeNext.right().right();

                Integer i = pair.left();
                Character c = pair.right();
                if (c == '"') {
                    queue = skipDoubleQuotes(queue);
                }

                if (c == '(' && depth == 0) return new Some<>(i);
                if (c == ')') depth++;
                if (c == '(') depth--;
            } else {
                break;
            }
        }
        return new None<>();
    }
}

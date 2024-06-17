package magma.compile.rule.split;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MembersSplitter implements Splitter {

    @Override
    public List<String> split(String input) {
        var current = new State();
        var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            var c = queue.pop();
            var state = current.append(c);

            if (c == '/' && state.isLevel() && !queue.isEmpty()) {
                var after = queue.peek();
                if (after == '/') {
                    // We are in a single-line comment.

                    var withAfter = state.append(queue.pop());
                    while (!queue.isEmpty()) {
                        var next = queue.pop();
                        withAfter = withAfter.append(next);
                        if (next == '\n') {
                            break;
                        }
                    }

                    current = withAfter.advance();
                    continue;
                } else if (after == '*') {
                    // We are in a block comment.

                    var withAfter = state.append(queue.pop());
                    while (!queue.isEmpty()) {
                        var next = queue.pop();
                        withAfter = withAfter.append(next);
                        if (next == '*' && !queue.isEmpty() && queue.peek() == '/') {
                            withAfter = withAfter.append(queue.pop());
                            break;
                        }
                    }

                    current = withAfter.advance();
                    continue;
                }
            }

            if (c == '\"') {
                var withString = current;
                while (!queue.isEmpty()) {
                    var next = queue.pop();
                    withString = withString.append(next);

                    if (next == '\\') {
                        withString.append(queue.pop());
                        continue;
                    }

                    if (next == '\"') {
                        break;
                    }
                }
                current = withString;
                continue;
            }

            if (c == ';' && state.isLevel()) {
                current = state.advance();
            } else if (c == '}' && state.isShallow()) {
                current = state.exit().advance();
            } else if (c == '{') {
                current = state.enter();
            } else if (c == '}') {
                current = state.exit();
            } else {
                current = state;
            }
        }

        return current.advance().tokens
                .stream()
                .filter(token -> !token.isBlank())
                .toList();
    }

    record State(List<String> tokens, StringBuilder buffer, int depth) {
        public State() {
            this(Collections.emptyList(), new StringBuilder(), 0);
        }

        private State advance() {
            var copy = new ArrayList<>(this.tokens);
            copy.add(this.buffer.toString());
            return new State(copy, new StringBuilder(), depth);
        }

        private State append(char c) {
            return new State(tokens, this.buffer.append(c), depth);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            return new State(tokens, buffer, depth + 1);
        }

        public State exit() {
            return new State(tokens, buffer, depth - 1);
        }

        public boolean isShallow() {
            return depth == 1;
        }
    }
}
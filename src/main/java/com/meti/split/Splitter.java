package com.meti.split;

import com.meti.safe.NativeString;
import com.meti.safe.SafeList;
import com.meti.safe.iter.Collectors;
import com.meti.safe.iter.Iterator;

public record Splitter(NativeString value) {
    private static State foldImpl(State state, Character c) {
        if (c == '}' && state.isShallow()) {
            return state.append(c)
                    .advance()
                    .lower();
        } else if (c == ';' && state.isLevel()) {
            return state.advance();
        } else {
            var appended = state.append(c);
            return switch (c) {
                case '{' -> appended.upper();
                case '}' -> appended.lower();
                default -> appended;
            };
        }
    }

    public SafeList<NativeString> split() {
        return value.iter()
                .foldLeft(new State(), Splitter::foldImpl)
                .advance()
                .iter()
                .map(NativeString::strip)
                .filter(NativeString::isNonEmpty)
                .collect(Collectors.toList());
    }

    record State(SafeList<NativeString> delimited,
                 NativeString buffer,
                 int depth) {
        State() {
            this(SafeList.empty(), NativeString.empty(), 0);
        }

        public State advance() {
            return new State(delimited.add(buffer), NativeString.empty(), depth);
        }

        public State append(char value) {
            return new State(delimited, buffer.concat(value), depth);
        }

        public State lower() {
            return new State(delimited, buffer, depth - 1);
        }

        public boolean isShallow() {
            return depth == 1;
        }

        public boolean isLevel() {
            return depth == 0;
        }

        public State upper() {
            return new State(delimited, buffer, depth + 1);
        }

        public Iterator<NativeString> iter() {
            return delimited.iter();
        }
    }
}

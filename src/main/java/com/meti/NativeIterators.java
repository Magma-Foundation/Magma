package com.meti;

import java.util.List;

public class NativeIterators {
    public static <T> Iterator<T> fromList(List<T> lines) {
        return new LIstIterator<>(lines);
    }

    private static class LIstIterator<T> extends IndexedIterator<T> {
        private final List<T> lines;

        public LIstIterator(List<T> lines) {
            super();
            this.lines = lines;
        }

        @Override
        protected int size() {
            return lines.size();
        }

        @Override
        protected Option<T> apply(int counter) {
            return new Some<>(lines.get(counter));
        }
    }
}
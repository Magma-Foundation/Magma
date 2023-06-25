package com.meti.safe.iter;

import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

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
            return Some.apply(lines.get(counter));
        }
    }
}

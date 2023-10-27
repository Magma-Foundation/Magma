package com.meti.api.collect;

import com.meti.api.iterator.AbstractIterator;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

class NativeListIterator<T> extends AbstractIterator<T> {
    private final java.util.List<T> inner;
    private int counter = 0;

    public NativeListIterator(java.util.List<T> inner) {
        this.inner = inner;
    }

    @Override
    public Option<T> head() {
        if (counter < inner.size()) {
            var temp = inner.get(counter);
            counter++;
            return Some.apply(temp);
        } else {
            return None.apply();
        }
    }
}

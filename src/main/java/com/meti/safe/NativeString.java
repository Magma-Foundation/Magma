package com.meti.safe;

import com.meti.safe.iter.IndexedIterator;
import com.meti.safe.iter.Iterator;
import com.meti.safe.iter.Iterators;
import com.meti.safe.iter.NativeIterators;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record NativeString(String internalValue) {
    public static NativeString from(String unwrap) {
        return new NativeString(unwrap);
    }

    public static NativeString empty() {
        return new NativeString("");
    }

    public Option<Index> firstIndexOfCharByPredicate(Predicate<Character> predicate) {
        return Iterators.fromRange(0, length()).zip(iter())
                .map(tuple -> predicate.test(tuple.right()) ? new Some<>(tuple.left()) : new None<Integer>())
                .flatMap(Iterators::fromOption)
                .head()
                .map((Integer index) -> new Index(index, internalValue.length()));
    }

    public String internalValue() {
        return internalValue;
    }

    public Option<Index> firstIndexOfChar(char c) {
        var value = internalValue.indexOf(c);
        return value == -1
                ? new None<>()
                : new Some<>(new Index(value, this.length()));
    }

    public NativeString slice(Range range) {
        return new NativeString(internalValue.substring(range.start(), range.end()));
    }

    public NativeString strip() {
        return from(internalValue.strip());
    }

    public int length() {
        return internalValue.length();
    }

    public boolean startsWith(NativeString other) {
        return internalValue.startsWith(other.internalValue);
    }

    public Iterator<Character> iter() {
        return new IndexedIterator<>() {
            @Override
            protected int size() {
                return internalValue.length();
            }

            @Override
            protected Option<Character> apply(int counter) {
                return new Some<>(internalValue.charAt(counter));
            }
        };
    }

    public boolean equalsTo(NativeString other) {
        return length() == other.length() &&
               iter().zip(other.iter()).allMatch(tuple -> tuple.left() == tuple.right());
    }

    public NativeString sliceTo(Index index) {
        return new NativeString(internalValue.substring(0, index.value()));
    }

    public Tuple2<NativeString, NativeString> splitExcludingAt(Index index) {
        var left = internalValue.substring(0, index.value());
        var right = internalValue.substring(index.value() + 1);
        return new Tuple2<>(new NativeString(left), new NativeString(right));
    }

    public Option<Index> firstIndexAfterSlice(NativeString value) {
        var index = internalValue.indexOf(value.internalValue);
        return index == -1 ? new None<>() : new Some<>(new Index(index + value.length(), this.length()));
    }

    public NativeString sliceFrom(Index index) {
        return new NativeString(internalValue.substring(index.value()));
    }

    public Option<Index> lastIndexOfChar(char c) {
        var value = internalValue.lastIndexOf(c);
        return value == -1
                ? new None<>()
                : new Some<>(new Index(value, this.length()));
    }

    public Iterator<NativeString> splitExcludingAtAll(String delimiter) {
        return NativeIterators.fromList(Arrays.stream(internalValue.split(delimiter))
                .map(NativeString::new)
                .collect(Collectors.toList()));
    }

    public boolean isNonEmpty() {
        return !internalValue.isEmpty();
    }

    public NativeString concat(char value) {
        return new NativeString(this.internalValue + value);
    }

}
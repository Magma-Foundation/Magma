package jvm.collect.string;

import magma.collect.stream.Collector;

public class NativeStringCollector implements Collector<Character, String> {
    @Override
    public String createInitial() {
        return "";
    }

    @Override
    public String fold(String s, Character character) {
        return s + character;
    }
}

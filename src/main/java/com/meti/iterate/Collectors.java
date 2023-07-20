package com.meti.iterate;

public class Collectors {
    public static Collector<Boolean, Boolean> or() {
        return new Collector<>() {
            @Override
            public Boolean initial() {
                return false;
            }

            @Override
            public Boolean foldLeft(Boolean accumulated, Boolean element) {
                return accumulated || element;
            }
        };
    }
}

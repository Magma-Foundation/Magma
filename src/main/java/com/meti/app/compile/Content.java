package com.meti.app.compile;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

public class Content implements Node {
    private final String_ value;

    public Content(String_ value) {
        this.value = value;
    }

    public static Node ofContent(String_ value) {
        return new Content(value);
    }

    @Override
    public Option<String_> value() {
        return Some.apply(value);
    }

    @Override
    public Option<List<? extends Node>> lines() {
        return None.apply();
    }

    @Override
    public Option<Node> type() {
        return None.apply();
    }

    @Override
    public Option<Node> body() {
        return None.apply();
    }

    @Override
    public Option<String_> name() {
        return None.apply();
    }

    @Override
    public Option<Set<? extends Node>> parameters() {
        return None.apply();
    }

    @Override
    public Option<Set<String_>> keywords() {
        return None.apply();
    }

    @Override
    public Option<String_> parent() {
        return None.apply();
    }

    @Override
    public Option<String_> child() {
        return None.apply();
    }

    @Override
    public Option<Node> returns() {
        return None.apply();
    }
}

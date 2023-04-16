package com.meti;

import java.util.ArrayList;
import java.util.List;

public record State(ImportCache cache, List<Node> others) {
    public State(){
        this(new ImportCache(), new ArrayList<>());
    }
}
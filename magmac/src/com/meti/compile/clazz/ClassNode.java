package com.meti.compile.clazz;

import com.meti.compile.Node;

public record ClassNode(String name, String body) implements Node {
}
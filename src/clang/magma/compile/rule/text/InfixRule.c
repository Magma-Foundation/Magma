#include "InfixRule.h"
expand Result_Node_CompileError
expand Result_String_CompileError
struct public InfixRule(struct Rule left, struct String infix, struct Rule right, struct Locator locator}{this.left = left;
        this.infix = infix;
        this.right = right;
        this.locator = locator;}struct Result_Node_CompileError parse(struct String input}{return locator.locate(input, infix).map(index -> {
            String left = input.substring(0, index);
            String right = input.substring(index + infix.length());
            return this.left.parse(left).and(() -> this.right.parse(right)).mapValue(tuple -> tuple.left().merge(tuple.right()));
        }).orElseGet(() -> {
            return new Err<>(new CompileError( + infix + , new StringContext(input)));
        });}struct Result_String_CompileError generate(struct Node node}{return left.generate(node).and(() -> right.generate(node)).mapValue(tuple -> tuple.left() + infix + tuple.right());}
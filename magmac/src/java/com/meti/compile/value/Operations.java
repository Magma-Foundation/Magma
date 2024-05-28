package com.meti.compile.value;

import com.meti.api.Streams;
import com.meti.api.option.Option;
import com.meti.compile.JavaString;
import com.meti.compile.Node;
import com.meti.node.Attribute;

import static com.meti.api.option.Options.$Option;

public class Operations {
    static Option<Node> lexOperator(JavaString stripped, JavaString operator) {
        return stripped.splitAtFirstSlice(operator).map(tuple -> {
            var left = tuple.left();
            var right = tuple.right();

            return new Node()
                    .withString("left", left)
                    .withString("right", right)
                    .withString("operator", operator);
        });
    }

    public static Option<Node> lex(JavaString stripped) {
        return Streams.from("&&", "==", "!=", "+", "||", "-", "<=", "<")
                .map(JavaString::new)
                .map(operator -> lexOperator(stripped, operator))
                .flatMap(Streams::fromOption)
                .head();
    }

    public static Option<JavaString> render(Node compiled) {
        return $Option(() -> {
            var left = compiled.apply("left").flatMap(Attribute::asString).$();
            var operator = compiled.apply("operator").flatMap(Attribute::asString).$();
            var right = compiled.apply("right").flatMap(Attribute::asString).$();

            return left.concatSlice(" ")
                    .concatOwned(operator)
                    .concatSlice(" ")
                    .concatOwned(right);
        });
    }
}
package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.compile.rule.RuleResult;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static com.meti.compile.scope.ClassLexer.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassLexerTest {
    @Test
    void extendsEmpty() {
        assertTrue(EXTENDS.apply(JavaString.Empty).isPresent());
    }

    @Test
    void extendsPresent() {
        assertTrue(EXTENDS.apply(JavaString.from("extends Super ")).isPresent());
    }

    @Test
    void rule() {
        var apply = RULE.apply(JavaString.from("class Test {}"));
        assertTrue(apply.isPresent());
    }

    @Test
    void rule_super() {
        assertTrue(RULE.apply(JavaString.from("class Test extends Super {}")).isPresent());
    }
}
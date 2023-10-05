package com.meti.compile.rule;

import com.meti.api.collect.Iterator;

public interface RuleLexer {
    Iterator<Rule> lex();
}

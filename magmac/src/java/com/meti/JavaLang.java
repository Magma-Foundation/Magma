package com.meti;

import com.meti.compile.Lang;
import com.meti.rule.*;

public class JavaLang {
    public static final FirstCharRule JAVA_DEFINITION = new FirstCharRule(Lang.VALUE_SEPARATOR,
            new StripRule(new LastCharSeparatorRule(new OrRule(new FirstRule(" ", new StringListRule("modifiers", " "), new StringRule("type")), new StringRule("type")), ' ', new StringRule("name"))),
            new RequireRightChar(new StripRule(new StringRule("value")), Lang.STATEMENT_END));
}

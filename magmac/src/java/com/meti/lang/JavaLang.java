package com.meti.lang;

import com.meti.rule.LazyRule;
import com.meti.rule.Rule;
import com.meti.rule.TypeRule;

import static com.meti.rule.DelimiterRule.Delimit;
import static com.meti.rule.DiscardRule.Empty;
import static com.meti.rule.DisjunctionRule.Or;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NodeRule.Node;
import static com.meti.rule.NodeSplitRule.Nodes;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.SplitByFirstSliceLeftInclusiveRule.FirstIncludeLeft;
import static com.meti.rule.SplitByFirstSliceRightInclusiveRule.FirstIncludeRight;
import static com.meti.rule.SplitByFirstSliceRule.First;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.StripRule.Strip;
import static com.meti.rule.TypeRule.Type;

public class JavaLang {
    public static final TypeRule IMPORT;
    public static final TypeRule METHOD;
    public static final Rule CLASS_MEMBER;

    public static final TypeRule CLASS;

    public static final Rule JAVA_ROOT;

    public static final TypeRule PACKAGE = Type("package ", Left("package ", $("namespace")));

    static {
        var segments = Last($("parent"), ".", $("child"));
        IMPORT = Type("import", Left("import ", Or(Left("static ", segments), segments)));

        var methodParam = Strip(First($("param-type"), " ", $("param-name")));
        var methodParams = Left("(", Right(methodParam, ")"));

        var methodBeforeParams = Strip(Last(Strip(Last(Strip(Delimit("modifiers", " ")), " ", $("return-type"))), " ", $("name")));
        var methodContent = blockOfStatements();

        METHOD = Type("method", Strip(FirstIncludeRight(methodBeforeParams, "(", FirstIncludeLeft(methodParams, ")",methodContent))));

        CLASS_MEMBER = Or(
                METHOD,
                Type("content", $("value"))
        );

        CLASS = Type("class", First(Empty, "class ", FirstIncludeRight(Strip($("name")), "{",
                Node("content", Type("block", Strip(Left("{", Right(Nodes("children", CLASS_MEMBER), "}"))))))));

        JAVA_ROOT = Nodes("roots", Strip(Or(
                PACKAGE,
                IMPORT,
                CLASS
        )));
    }

    private static Rule blockOfStatements() {
        var lazy = new LazyRule();

        var methodMembers = Or(
                Type("try", Strip(Left("try", lazy))),
                Type("catch", Strip(Left("catch", First(Strip(Left("(", $("condition"))),")", lazy)))),
                Type("declaration", First(Strip(First($("type"), " ", $("name"))), "=", Strip($("value")))),
                Type("content", $("value"))
        );

        var strip = Node("value", Type("block", Strip(Left("{", Right(Nodes("children", methodMembers), "}")))));
        lazy.setRule(strip);
        return strip;
    }
}

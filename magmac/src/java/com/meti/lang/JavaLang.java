package com.meti.lang;

import com.meti.rule.Rule;
import com.meti.rule.TypeRule;

import java.util.List;
import java.util.Map;

import static com.meti.lang.Lang.Block;
import static com.meti.rule.DiscardRule.Discard;
import static com.meti.rule.DisjunctionRule.Or;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NodeRule.Node;
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
    public static final Map<String, List<Rule>> JAVA_ROOT;

    static {
        var segments = Last($("parent"), ".", $("child"));
        IMPORT = Type("import", Left("import ", Or(Left("static ", segments), segments)));

        var methodParam = Strip(First($("param-type"), " ", $("param-name")));
        var methodParams = Left("(", Right(methodParam, ")"));

        METHOD = Type("method", Strip(Left("public static void ",
                FirstIncludeRight($("name"), "(", FirstIncludeLeft(methodParams, ")", $("content"))))));

        CLASS_MEMBER = Or(
                METHOD,
                Type("content", $("value"))
        );

        CLASS = Type("class", First(Discard, "class ", FirstIncludeRight(Strip($("name")), "{",
                Node("content", Type("block", Block(CLASS_MEMBER))))));

        JAVA_ROOT = Map.of("root", List.of(
                IMPORT,
                CLASS));
    }
}

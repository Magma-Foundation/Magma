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
import static com.meti.rule.SplitByFirstSliceInclusiveRule.FirstInclusive;
import static com.meti.rule.SplitByFirstSliceRule.First;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.StripRule.Strip;
import static com.meti.rule.TypeRule.Type;

public class JavaLang {
    public static final TypeRule IMPORT;
    public static final TypeRule METHOD = Type("method", Strip(Left("public static void ", FirstInclusive($("name"), "(", First($("param-segment"), ")", $("content"))))));
    public static final Rule CLASS_MEMBER = Or(
            METHOD,
            Type("content", $("value"))
    );
    public static final TypeRule CLASS = Type("class", First(Discard, "class ", FirstInclusive(Strip($("name")), "{",
            Node("content", Type("block", Block(CLASS_MEMBER))))));
    public static final Map<String, List<Rule>> JAVA_ROOT;

    static {
        var segments = Last($("parent"), ".", $("child"));
        IMPORT = Type("import", Left("import ", Or(Left("static ", segments), segments)));

        JAVA_ROOT = Map.of("root", List.of(
                IMPORT,
                CLASS));
    }
}

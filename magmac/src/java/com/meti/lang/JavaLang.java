package com.meti.lang;

import com.meti.rule.TypeRule;
import com.meti.rule.Rule;

import java.util.List;
import java.util.Map;

import static com.meti.rule.DiscardRule.Discard;
import static com.meti.rule.DisjunctionRule.Or;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.TypeRule.Type;
import static com.meti.rule.NodeRule.Node;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.SplitByFirstSliceInclusiveRule.FirstInclusive;
import static com.meti.rule.SplitByFirstSliceRule.First;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.StripRule.Strip;

public class JavaLang {
    public static final TypeRule IMPORT;

    static {
        var segments = Last($("parent"), ".", $("child"));
        var last = Or(Left("static ", segments), segments);
        IMPORT = Type("import", Left("import ", last));
    }

    public static final TypeRule CLASS = Type("class", First(Discard, "class ", FirstInclusive(Strip($("name")), "{",
            Node("content", Type("block", Lang.BLOCK)))));
    public static final Map<String, List<Rule>> JAVA_ROOT = Map.of("root", List.of(
            IMPORT,
            CLASS));
}

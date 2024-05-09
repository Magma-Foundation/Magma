package com.meti.lang;

import com.meti.rule.DisjunctionRule;
import com.meti.rule.NamingRule;
import com.meti.rule.Rule;

import java.util.List;
import java.util.Map;

import static com.meti.rule.DiscardRule.Discard;
import static com.meti.rule.DisjunctionRule.Or;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NamingRule.Name;
import static com.meti.rule.NodeRule.Node;
import static com.meti.rule.NodeSplitRule.Nodes;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.SplitByFirstSliceInclusiveRule.FirstInclusive;
import static com.meti.rule.SplitByFirstSliceRule.First;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.StripRule.Strip;

public class JavaLang {
    public static final Rule BLOCK = Strip(Left("{", Right(Nodes("children", Name("block-member", $("value"))), "}")));
    public static final NamingRule IMPORT;

    static {
        var segments = Last($("parent"), ".", $("child"));
        var last = Or(Left("static ", segments), segments);
        IMPORT = Name("import", Left("import ", last));
    }

    public static final Map<String, List<Rule>> ROOT_RULES = Map.of("root", List.of(
            IMPORT,
            Name("class", First(Discard, "class ", FirstInclusive(Strip($("name")), "{",
                    Node("content", Name("block", BLOCK)))))));
}

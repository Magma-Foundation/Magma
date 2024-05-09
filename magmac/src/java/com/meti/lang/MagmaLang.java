package com.meti.lang;

import com.meti.rule.Rule;

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

public class MagmaLang {
    public static final Rule MAGMA_ROOT;

    static {
        var methodParam = Strip(First($("param-name"), " : ", $("param-type")));
        var methodParams = Left("(", Right(methodParam, ")"));

        var methodReturnTypeRule = Or(Left(": ", $("return-type")), Empty);
        var content = Nodes("children", Or(Type("content", $("value"))));

        var left = First(methodReturnTypeRule, " => ", Right(Left("{", content), "\n\t}\n"));

        var methodRule = Type("method", Strip(Left("\tdef ",
                FirstIncludeRight($("name"), "(", FirstIncludeLeft(methodParams, ")", left)))));

        Rule child = Or(methodRule, Type("content", $("value")));
        var blockRule = Strip(Left("{\n", Right(Nodes("children", child), "}")));

        var functionRule = Type("class", First(Empty, "class def ", First(Strip($("name")), "() => ", Node("content", Type("block", blockRule)))));

        var importRule = Type("import", Left("import ", Right(Last(Left("{ ", Right($("child"), " }")), " from ", $("parent")), ";\n")));
        MAGMA_ROOT = Or(importRule, functionRule);
    }
}

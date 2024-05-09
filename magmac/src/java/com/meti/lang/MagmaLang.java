package com.meti.lang;

import com.meti.rule.LazyRule;
import com.meti.rule.Rule;
import com.meti.rule.TypeRule;

import static com.meti.rule.DiscardRule.Empty;
import static com.meti.rule.DisjunctionRule.Or;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NodeRule.Node;
import static com.meti.rule.NodeSplitRule.Nodes;
import static com.meti.rule.QuantitySplitRule.Quantities;
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
    public static final TypeRule DECLARATION = Type("declaration", First(Strip(First(First($("indent"), "let ", $("name")), " : ", $("type"))), " = ", Strip(Right($("value"), ";\n"))));

    static {
        var methodParam = Type("param", Strip(First($("param-name"), " : ", $("param-type"))));
        var methodParams = Left("(", Right(Quantities("params", methodParam), ")"));

        var methodReturnTypeRule = Or(Left(": ", $("return-type")), Empty);
        var right = blockOfStatements();

        var left = First(methodReturnTypeRule, " => ", right);

        var methodRule = Type("method", Strip(First($("indent"), "def ", FirstIncludeRight($("name"), "(", FirstIncludeLeft(methodParams, ")", left)))));

        Rule child = Or(methodRule, Type("content", $("value")));
        var blockRule = Strip(Left("{\n", Right(Nodes("children", child), "}")));

        var functionRule = Type("class", First(Empty, "class def ", First(Strip($("name")), "() => ", Node("content", Type("block", blockRule)))));

        var importRule = Type("import", Left("import ", Right(Last(Left("{ ", Right($("child"), " }")), " from ", $("parent")), ";\n")));
        MAGMA_ROOT = Or(importRule, functionRule);
    }

    private static Rule blockOfStatements() {
        var lazy = new LazyRule();

        var methodContent = Nodes("children", Or(
                Type("try", Strip(First($("indent"), "try ", lazy))),
                Type("catch", Strip(First($("indent"), "catch ", First(Strip(Left("(", $("condition"))),")", lazy)))),
                DECLARATION,
                Type("content", $("value"))
        ));

        var rule = First(Left("{\n", methodContent), "\n", Right($("indent"), "}\n"));
        var root = Node("value", Type("block", rule));
        lazy.setRule(root);
        return root;
    }
}

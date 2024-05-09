package com.meti.lang;

import com.meti.rule.Rule;
import com.meti.rule.StripRule;
import com.meti.rule.TypeRule;

import static com.meti.lang.Lang.Block;
import static com.meti.rule.DiscardRule.Discard;
import static com.meti.rule.DisjunctionRule.Or;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NodeRule.Node;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.SplitByFirstSliceInclusiveRule.FirstInclusive;
import static com.meti.rule.SplitByFirstSliceRule.First;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.StripRule.Strip;
import static com.meti.rule.TypeRule.Type;

public class MagmaLang {
    public static final TypeRule METHOD = Type("method", Strip(Left("class def ", FirstInclusive($("name"), "(", First($("param-segment"), ") =>", $("content"))))));
    public static final TypeRule IMPORT = Type("import", Left("import ", Right(Last(Left("{ ", Right($("child"), " }")), " from ", $("parent")), ";\n")));
    public static final StripRule Block = Block(Or(METHOD, Type("content", $("value"))));
    public static final TypeRule FUNCTION = Type("class", First(Discard, "class def ", First(Strip($("name")), "() => ", Node("content", Type("block", Block)))));
    public static final Rule MAGMA_ROOT = Or(IMPORT, FUNCTION);
}

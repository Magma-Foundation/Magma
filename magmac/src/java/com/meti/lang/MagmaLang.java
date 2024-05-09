package com.meti.lang;

import com.meti.rule.DisjunctionRule;
import com.meti.rule.Rule;
import com.meti.rule.TypeRule;

import static com.meti.rule.DiscardRule.Discard;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NodeRule.Node;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.SplitByFirstSliceRule.First;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.StripRule.Strip;
import static com.meti.rule.TypeRule.Type;

public class MagmaLang {
    public static final TypeRule IMPORT = Type("import", Left("import ", Right(Last(Left("{ ", Right($("child"), " }")), " from ", $("parent")), ";\n")));
    public static final TypeRule FUNCTION = Type("class", First(Discard, "class def ", First(Strip($("name")), "() => ", Node("content", Type("block", Lang.BLOCK)))));
    public static final Rule MAGMA_ROOT = DisjunctionRule.Or(IMPORT, FUNCTION);
}

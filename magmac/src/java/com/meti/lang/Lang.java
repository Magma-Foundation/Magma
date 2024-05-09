package com.meti.lang;

import com.meti.rule.Rule;

import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NodeSplitRule.Nodes;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.StripRule.Strip;
import static com.meti.rule.TypeRule.Type;

public class Lang {
    public static final Rule BLOCK = Strip(Left("{", Right(Nodes("children", Type("block-member", $("value"))), "}")));
}

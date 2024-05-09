package com.meti.lang;

import com.meti.rule.Rule;
import com.meti.rule.StripRule;

import static com.meti.rule.NodeSplitRule.Nodes;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.StripRule.Strip;

public class Lang {
    public static StripRule Block(Rule child) {
        return Strip(Left("{", Right(Nodes("children", child), "}")));
    }
}

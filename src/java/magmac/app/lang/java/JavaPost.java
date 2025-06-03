package magmac.app.lang.java;

import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.node.JavaValue;
import magmac.app.lang.node.Post;
import magmac.app.lang.node.PostVariant;

public final class JavaPost extends Post implements JavaFunctionSegmentValue {
    public JavaPost(PostVariant variant, JavaValue value) {
        super(variant, value);
    }

    public static Rule createPostRule(String type, String suffix, Rule value) {
        return new TypeRule(type, new StripRule(new SuffixRule(new NodeRule("child", value), suffix)));
    }

    public PostVariant variant() {
        return variant;
    }

    public JavaValue value() {
        return value;
    }
}

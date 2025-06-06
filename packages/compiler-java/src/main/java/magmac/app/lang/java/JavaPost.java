package magmac.app.lang.java;

import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.node.Post;
import magmac.app.lang.node.PostVariant;

/**
 * Represents a postfix operation such as {@code value++} or {@code value--}.
 * The {@link PostVariant} determines the specific operator while the child
 * {@link JavaLang.Value} holds the expression being modified.
 */
public final class JavaPost extends Post implements JavaFunctionSegmentValue {
    public JavaPost(PostVariant variant, JavaLang.Value value) {
        super(variant, value);
    }

    public static Rule createPostRule(String type, String suffix, Rule value) {
        return new TypeRule(type, new StripRule(new SuffixRule(new NodeRule("child", value), suffix)));
    }

    public PostVariant variant() {
        return this.variant;
    }

    public JavaLang.Value value() {
        return this.value;
    }
}

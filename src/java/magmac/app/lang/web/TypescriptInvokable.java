package magmac.app.lang.web;

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Invokable;

public class TypescriptInvokable extends Invokable<TypescriptCaller, TypescriptLang.TypescriptArgument> implements TypescriptLang.TypescriptValue, TypescriptFunctionSegmentValue {
    public TypescriptInvokable(TypescriptCaller caller, List<TypescriptLang.TypescriptArgument> arguments) {
        super(caller, arguments);
    }

    @Override
    public Node serialize() {
        return new MapNode("invokable")
                .withNodeSerialized("caller", this.caller)
                .withNodeListSerialized("arguments", this.arguments);
    }
}

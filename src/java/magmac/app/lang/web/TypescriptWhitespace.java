package magmac.app.lang.web;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.node.TypeScriptParameter;
import magmac.app.lang.node.TypeScriptRootSegment;
import magmac.app.lang.node.TypescriptFunctionSegment;
import magmac.app.lang.node.TypescriptStructureMember;

public class TypescriptWhitespace implements
        TypeScriptRootSegment,
        TypescriptStructureMember,
        TypeScriptParameter,
        TypescriptFunctionSegment {
    @Override
    public Node serialize() {
        return new MapNode("whitespace");
    }
}

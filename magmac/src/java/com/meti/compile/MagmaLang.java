package com.meti.compile;

import com.meti.node.Attribute;
import com.meti.node.IntAttribute;
import com.meti.node.StringAttribute;

import java.util.Map;

public class MagmaLang {
    static String function(int indent, String modifierString, String name, String renderedParams, String typeString, String content) {
        return renderFunctionDeclaration(Map.of(
                "indent", new IntAttribute(indent),
                "modifiers", new StringAttribute(modifierString + "def "),
                "name", new StringAttribute(name),
                "params", new StringAttribute(renderedParams),
                "type", new StringAttribute(typeString)
        )) + " => " + content + "\n";
    }

    static String renderFunctionDeclaration(Map<String, Attribute> node) {
        var indent1 = node.get("indent").asInt().orElseThrow();
        var modifiers1 = node.get("modifiers").asString().orElseThrow();
        var name1 = node.get("name").asString().orElseThrow();
        var params1 = node.get("params").asString().orElseThrow();
        var type1 = node.get("type").asString().orElseThrow();
        return "\t".repeat(indent1) + modifiers1 + name1 + "(" + params1 + ")" + type1;
    }

    static String renderClass(String modifierString, String name, ClassMemberResult members, String paramString) {
        var instanceContent = renderInstanceClassContent(modifierString, name, members, paramString);
        var staticContent = renderStaticClassContent(modifierString, name, members);
        return instanceContent + staticContent;
    }

    private static String renderInstanceClassContent(String modifierString, String name, ClassMemberResult members, String paramString) {
        var joinedInstance = String.join("", members.instanceMembers());

        return function(0, modifierString + "class ", name, paramString, "", "{\n" + joinedInstance + "}");
    }

    private static String renderStaticClassContent(String modifierString, String name, ClassMemberResult members) {
        if (members.staticMembers().isEmpty()) return "";

        var joinedStatic = String.join("", members.staticMembers());
        return modifierString + "object " + name + " {\n" + joinedStatic + "}";
    }
}

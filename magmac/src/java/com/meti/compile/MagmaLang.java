package com.meti.compile;

import com.meti.api.option.Options;
import com.meti.node.Attribute;
import com.meti.node.IntAttribute;
import com.meti.node.StringAttribute;

import java.util.Map;

public class MagmaLang {
    static String renderFunction(Map<String, Attribute> node) {
        Attribute attribute = node.get("content");
        var content1 = Options.toNative(attribute.asString().map(JavaString::value)).orElseThrow();
        return renderFunctionDeclaration(node) + " => " + content1 + "\n";
    }

    static String renderFunctionDeclaration(Map<String, Attribute> node) {
        var indent1 = node.get("indent").asInt().orElseThrow();
        Attribute attribute3 = node.get("modifiers");
        var modifiers1 = Options.toNative(attribute3.asString().map(JavaString::value)).orElseThrow();
        Attribute attribute2 = node.get("name");
        var name1 = Options.toNative(attribute2.asString().map(JavaString::value)).orElseThrow();
        Attribute attribute1 = node.get("params");
        var params1 = Options.toNative(attribute1.asString().map(JavaString::value)).orElseThrow();
        Attribute attribute = node.get("type");
        var type1 = Options.toNative(attribute.asString().map(JavaString::value)).orElseThrow();
        return "\t".repeat(indent1) + modifiers1 + name1 + "(" + params1 + ")" + type1;
    }

    static String renderClass(String modifierString, String name, ClassMemberResult members, String paramString) {
        var instanceContent = renderInstanceClassContent(modifierString, name, members, paramString);
        var staticContent = renderStaticClassContent(modifierString, name, members);
        return instanceContent + staticContent;
    }

    private static String renderInstanceClassContent(String modifierString, String name, ClassMemberResult members, String paramString) {
        var joinedInstance = String.join("", members.instanceMembers());

        return renderFunction(Map.of(
                "indent", new IntAttribute(0),
                "modifiers", new StringAttribute(modifierString + "class " + "def "),
                "name", new StringAttribute(name),
                "params", new StringAttribute(paramString),
                "type", new StringAttribute(""),
                "content", new StringAttribute("{\n" + joinedInstance + "}")
        ));
    }

    private static String renderStaticClassContent(String modifierString, String name, ClassMemberResult members) {
        if (members.staticMembers().isEmpty()) return "";

        var joinedStatic = String.join("", members.staticMembers());
        return modifierString + "object " + name + " {\n" + joinedStatic + "}";
    }

    static String renderFunctionDeclaration(Node copy) {
        return renderFunctionDeclaration(copy.attributes());
    }

    static String renderFunction(Node copy) {
        return renderFunction(copy.attributes());
    }
}

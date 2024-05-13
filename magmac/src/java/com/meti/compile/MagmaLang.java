package com.meti.compile;

public class MagmaLang {
    static String function(int indent, String modifierString, String name, String renderedParams, String typeString, String content) {
        return declareFunction(indent, modifierString + "def ", name, renderedParams, typeString) + " => " + content + "\n";
    }

    static String declareFunction(int indent, String modifiers, String name, String renderedParams, String typeString) {
        return "\t".repeat(indent) + modifiers + name + "(" + renderedParams + ")" + typeString;
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

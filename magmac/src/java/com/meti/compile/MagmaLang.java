package com.meti.compile;

public class MagmaLang {
    static String renderFunction(String modifierString, String name, String renderedParams, String typeString, int indent, String content) {
        var indentString = "\t".repeat(indent);

        return indentString +
               modifierString +
               "def " + name + "(" + renderedParams + ")" +
               typeString +
               " => {\n" +
               content +
               indentString + "}\n";
    }

    static String renderClass(String modifierString, String name, ClassMemberResult members) {
        var instanceContent = renderInstanceClassContent(modifierString, name, members);
        var staticContent = renderStaticClassContent(modifierString, name, members);
        return instanceContent + staticContent;
    }

    private static String renderInstanceClassContent(String modifierString, String name, ClassMemberResult members) {
        var joinedInstance = String.join("", members.instanceMembers());

        return renderFunction(modifierString + "class ", name, "", "", 0, joinedInstance);
    }

    private static String renderStaticClassContent(String modifierString, String name, ClassMemberResult members) {
        if (members.staticMembers().isEmpty()) return "";

        var joinedStatic = String.join("", members.staticMembers());
        return modifierString + "object " + name + " {\n" + joinedStatic + "}";
    }
}

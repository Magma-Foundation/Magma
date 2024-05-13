package com.meti.compile;

public class MagmaLang {
    static String renderDefinedFunction(int indent, String modifierString, String name, String renderedParams, String typeString, String content) {
        var indentString = "\t".repeat(indent);
        return renderFunction(indent, modifierString +
                                      "def ", name, renderedParams, typeString, "{\n" +
                                                                                content +
                                                                                indentString + "}");
    }

    static String renderFunction(int indent, String modifierString, String name, String renderedParams, String typeString, String content) {
        var indentString = "\t".repeat(indent);

        return indentString +
               modifierString + name + "(" + renderedParams + ")" +
               typeString +
               " => " + content + "\n";
    }

    static String renderClass(String modifierString, String name, ClassMemberResult members, String paramString) {
        var instanceContent = renderInstanceClassContent(modifierString, name, members, paramString);
        var staticContent = renderStaticClassContent(modifierString, name, members);
        return instanceContent + staticContent;
    }

    private static String renderInstanceClassContent(String modifierString, String name, ClassMemberResult members, String paramString) {
        var joinedInstance = String.join("", members.instanceMembers());

        return renderDefinedFunction(0, modifierString + "class ", name, paramString, "", joinedInstance);
    }

    private static String renderStaticClassContent(String modifierString, String name, ClassMemberResult members) {
        if (members.staticMembers().isEmpty()) return "";

        var joinedStatic = String.join("", members.staticMembers());
        return modifierString + "object " + name + " {\n" + joinedStatic + "}";
    }
}

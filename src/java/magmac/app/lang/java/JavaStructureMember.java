package magmac.app.lang.java;

public sealed interface JavaStructureMember permits JavaEnumValues, JavaMethod, JavaLang.Structure, JavaStructureStatement, JavaLang.Whitespace, JavaLang.Comment {
}

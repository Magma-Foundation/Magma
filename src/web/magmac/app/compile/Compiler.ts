export interface Compiler {
	 parseAndGenerate( units : UnitSet<JavaLang.Root>) : CompileResult<UnitSet<String>>;
}

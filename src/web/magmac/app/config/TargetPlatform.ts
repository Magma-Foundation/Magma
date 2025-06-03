export interface TargetPlatform {
	 createApplication() : Application;
	 createCompiler() : Compiler;
	 createTargetPath() : Path;
	 createExtension() : String;
	 createRule() : Rule;
}

import { Application } from "../../../magmac/app/Application";
import { Compiler } from "../../../magmac/app/compile/Compiler";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { Path } from "../../../java/nio/file/Path";
export interface TargetPlatform {
	createApplication() : Application;
	createCompiler() : Compiler;
	createTargetPath() : Path;
	createExtension() : String;
	createRule() : Rule;
}

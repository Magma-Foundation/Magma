import { Application } from "../../../magmac/app/Application";
import { CompileApplication } from "../../../magmac/app/CompileApplication";
import { Compiler } from "../../../magmac/app/compile/Compiler";
import { StagedCompiler } from "../../../magmac/app/compile/StagedCompiler";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { PathTargets } from "../../../magmac/app/io/targets/PathTargets";
import { Targets } from "../../../magmac/app/io/targets/Targets";
import { TypescriptLang } from "../../../magmac/app/lang/web/TypescriptLang";
import { JavaLang } from "../../../magmac/app/lang/java/JavaLang";
import { TypescriptRules } from "../../../magmac/app/lang/web/TypescriptRules";
import { Generator } from "../../../magmac/app/stage/generate/Generator";
import { RuleGenerator } from "../../../magmac/app/stage/generate/RuleGenerator";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { Path } from "../../../java/nio/file/Path";
import { Paths } from "../../../java/nio/file/Paths";
export class TypeScriptTargetPlatform {
	public createTargetPath() : Path {return Paths.get( ".", "src", "web");;}
	public createExtension() : String {return "ts";;}
	public createRule() : Rule {return TypescriptRules.createRule( );;}
	public createCompiler() : Compiler { let parser : Parser<JavaLang.JavaRoot, TypescriptLang.TypescriptRoot>=new JavaTypescriptParser( ); let generator : Generator=new RuleGenerator( this.createRule( ));return new StagedCompiler<TypescriptLang.TypescriptRoot>( parser, generator);;}
	public createApplication() : Application { let targetPath : var=this.createTargetPath( ); let extension : var=this.createExtension( ); let targets : Targets=new PathTargets( targetPath, extension);return new CompileApplication<>( this.createCompiler( ), targets);;}
}

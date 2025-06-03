import { Application } from "../../../magmac/app/Application";
import { CompileApplication } from "../../../magmac/app/CompileApplication";
import { Compiler } from "../../../magmac/app/compile/Compiler";
import { StagedCompiler } from "../../../magmac/app/compile/StagedCompiler";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { PathTargets } from "../../../magmac/app/io/targets/PathTargets";
import { Targets } from "../../../magmac/app/io/targets/Targets";
import { JavaPlantUMLParser } from "../../../magmac/app/lang/JavaPlantUMLParser";
import { PlantUMLRoot } from "../../../magmac/app/lang/node/PlantUMLRoot";
import { Generator } from "../../../magmac/app/stage/generate/Generator";
import { RuleGenerator } from "../../../magmac/app/stage/generate/RuleGenerator";
import { Path } from "../../../java/nio/file/Path";
import { Paths } from "../../../java/nio/file/Paths";
export class PlantUMLTargetPlatform {
	public createTargetPath() : Path {return Paths.get( ".", "diagrams");;}
	public createExtension() : String {return "puml";;}
	public createRule() : Rule {return PlantUMLRoot.createRule( );;}
	public createCompiler() : Compiler { let generator : Generator=new RuleGenerator( this.createRule( ));return new StagedCompiler<PlantUMLRoot>( new JavaPlantUMLParser( ), generator);;}
	private createApplication0( targets : Targets) : Application {return new CompileApplication<>( createCompiler( ), targets);;}
	public createApplication() : Application { let targetPath : var=this.createTargetPath( ); let extension : var=this.createExtension( ); let targets : Targets=new PathTargets( targetPath, extension);return this.createApplication0( targets);;}
}

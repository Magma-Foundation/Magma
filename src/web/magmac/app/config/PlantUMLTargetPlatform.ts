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
	createTargetPath() : Path {break;;}
	createExtension() : String {break;;}
	createRule() : Rule {break;;}
	createCompiler() : Compiler {break;break;;}
	createApplication0(targets : Targets) : Application {break;;}
	createApplication() : Application {break;break;break;break;;}
}

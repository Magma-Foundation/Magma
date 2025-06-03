import { Console } from "../jvm/io/Console";
import { Option } from "../magmac/api/Option";
import { Some } from "../magmac/api/Some";
import { Error } from "../magmac/api/error/Error";
import { Iters } from "../magmac/api/iter/Iters";
import { Result } from "../magmac/api/result/Result";
import { LexingStage } from "../magmac/app/LexingStage";
import { PlantUMLTargetPlatform } from "../magmac/app/config/PlantUMLTargetPlatform";
import { TargetPlatform } from "../magmac/app/config/TargetPlatform";
import { TypeScriptTargetPlatform } from "../magmac/app/config/TypeScriptTargetPlatform";
import { ApplicationError } from "../magmac/app/error/ApplicationError";
import { PathSources } from "../magmac/app/io/sources/PathSources";
import { Sources } from "../magmac/app/io/sources/Sources";
import { JavaDeserializers } from "../magmac/app/lang/JavaDeserializers";
import { JavaRules } from "../magmac/app/lang/JavaRules";
import { JavaRoot } from "../magmac/app/lang/node/JavaRoot";
import { Lexer } from "../magmac/app/stage/lexer/Lexer";
import { RuleLexer } from "../magmac/app/stage/lexer/RuleLexer";
import { UnitSet } from "../magmac/app/stage/unit/UnitSet";
import { Paths } from "../java/nio/file/Paths";
export class Main {
	main() : void {break;break;;}
	getNext(result : UnitSet<JavaRoot>) : Option<Error> {break;;}
	loadSources(sources : Sources) : Result<UnitSet<JavaRoot>, ApplicationError> {break;break;;}
	run(roots : UnitSet<JavaRoot>, platform : TargetPlatform) : Option<Error> {break;;}
}

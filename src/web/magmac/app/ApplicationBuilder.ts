import { Option } from "../../magmac/api/Option";
import { Error } from "../../magmac/api/error/Error";
import { Compiler } from "../../magmac/app/compile/Compiler";
import { StagedCompiler } from "../../magmac/app/compile/StagedCompiler";
import { TargetPlatform } from "../../magmac/app/config/TargetPlatform";
import { Sources } from "../../magmac/app/io/sources/Sources";
import { PathTargets } from "../../magmac/app/io/targets/PathTargets";
import { Targets } from "../../magmac/app/io/targets/Targets";
import { FlattenJava } from "../../magmac/app/lang/FlattenJava";
import { JavaLang } from "../../magmac/app/lang/JavaLang";
import { AfterAll } from "../../magmac/app/stage/AfterAll";
import { Passer } from "../../magmac/app/stage/Passer";
import { Generator } from "../../magmac/app/stage/generate/Generator";
import { RuleGenerator } from "../../magmac/app/stage/generate/RuleGenerator";
import { Lexer } from "../../magmac/app/stage/lexer/Lexer";
import { RuleLexer } from "../../magmac/app/stage/lexer/RuleLexer";
import { Parser } from "../../magmac/app/stage/parse/Parser";
import { TreeParser } from "../../magmac/app/stage/parse/TreeParser";
import { Path } from "../../java/nio/file/Path";
export class ApplicationBuilder {
	run(platform : TargetPlatform, sources : Sources) : Option<Error>;
}

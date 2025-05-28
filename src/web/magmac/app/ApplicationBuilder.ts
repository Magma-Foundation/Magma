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
	run(platform : TargetPlatform, sources : Sources) : Option<Error> { Path targetPath=platform.createTargetPath( ); String extension=platform.createExtension( ); Targets targets=new PathTargets( targetPath, extension); Lexer lexer=new RuleLexer( JavaLang.createRule( )); AfterAll afterAllChildren=platform.createAfterAll( ); Passer afterChild=platform.createAfterChild( ); Parser parser=new TreeParser( new FlattenJava( ), afterChild, afterAllChildren); Generator generator=new RuleGenerator( platform.createRule( )); Compiler compiler=new StagedCompiler( lexer, parser, generator); Application application=new CompileApplication( sources, compiler, targets);return application.run( );}
}

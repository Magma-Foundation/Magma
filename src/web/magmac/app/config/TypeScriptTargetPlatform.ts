import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { TypeScriptAfterPasser } from "../../../magmac/app/lang/TypeScriptAfterPasser";
import { TypescriptLang } from "../../../magmac/app/lang/TypescriptLang";
import { AfterAll } from "../../../magmac/app/stage/AfterAll";
import { EmptyAfterAll } from "../../../magmac/app/stage/EmptyAfterAll";
import { Passer } from "../../../magmac/app/stage/Passer";
import { Path } from "../../../java/nio/file/Path";
import { Paths } from "../../../java/nio/file/Paths";
export class TypeScriptTargetPlatform {
	public createAfterChild() : Passer {
		return new TypeScriptAfterPasser( );
	}
	public createTargetPath() : Path {
		return Paths.get( ".", "src", "web");
	}
	public createAfterAll() : AfterAll {
		return new EmptyAfterAll( );
	}
	public createExtension() : String {
		return "ts";
	}
	public createRule() : Rule {
		return TypescriptLang.createRule( );
	}
}

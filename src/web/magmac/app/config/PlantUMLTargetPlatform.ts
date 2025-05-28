import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { PlantUMLAfterPasser } from "../../../magmac/app/lang/PlantUMLAfterPasser";
import { MergeDiagram } from "../../../magmac/app/lang/MergeDiagram";
import { PlantUMLLang } from "../../../magmac/app/lang/PlantUMLLang";
import { AfterAll } from "../../../magmac/app/stage/after/AfterAll";
import { Passer } from "../../../magmac/app/stage/Passer";
import { Path } from "../../../java/nio/file/Path";
import { Paths } from "../../../java/nio/file/Paths";
export class PlantUMLTargetPlatform {
	public createAfterChild() : Passer {
		return new PlantUMLAfterPasser( );
	}
	public createTargetPath() : Path {
		return Paths.get( ".", "diagrams");
	}
	public createAfterAll() : AfterAll {
		return new MergeDiagram( );
	}
	public createExtension() : String {
		return "puml";
	}
	public createRule() : Rule {
		return PlantUMLLang.createRule( );
	}
}

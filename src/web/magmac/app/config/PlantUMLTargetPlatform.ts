import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { PlantUMLAfterPasser } from "../../../magmac/app/lang/PlantUMLAfterPasser";
import { MergeDiagram } from "../../../magmac/app/lang/MergeDiagram";
import { PlantUMLLang } from "../../../magmac/app/lang/PlantUMLLang";
import { AfterAll } from "../../../magmac/app/stage/AfterAll";
import { Passer } from "../../../magmac/app/stage/Passer";
import { Path } from "../../../java/nio/file/Path";
import { Paths } from "../../../java/nio/file/Paths";
export class PlantUMLTargetPlatform {
	createAfterChild() : Passer {
		return new PlantUMLAfterPasser( );
	}
	createTargetPath() : Path {
		return Paths.get( ".", "diagrams");
	}
	createAfterAll() : AfterAll {
		return new MergeDiagram( );
	}
	createExtension() : String {
		return "puml";
	}
	createRule() : Rule {
		return PlantUMLLang.createRule( );
	}
}

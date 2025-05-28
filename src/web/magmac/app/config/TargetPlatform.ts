import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { AfterAll } from "../../../magmac/app/stage/AfterAll";
import { Passer } from "../../../magmac/app/stage/Passer";
import { Path } from "../../../java/nio/file/Path";
export interface TargetPlatform {createAfterChild() : Passer;createTargetPath() : Path;createAfterAll() : AfterAll;createExtension() : String;createRule() : Rule;
}

import { Option } from "../../../../magmac/api/Option";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { Unit } from "../../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
import { IOException } from "../../../../java/io/IOException";
import { Files } from "../../../../java/nio/file/Files";
import { Path } from "../../../../java/nio/file/Path";
export class PathTargets {
	writeAll(outputs : UnitSet<String>) : Option<IOException> {return 0.iter( ).map( 0.write).flatMap( 0.fromOption).next( );;}
	write(entry : Unit<String>) : Option<IOException> {return 0.destruct( 0);;}
}

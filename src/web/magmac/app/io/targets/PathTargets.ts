import { Option } from "../../../../magmac/api/Option";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { IOException } from "../../../../java/io/IOException";
import { Files } from "../../../../java/nio/file/Files";
import { Path } from "../../../../java/nio/file/Path";
export class PathTargets {
	write(location : Location, output : String) : Option<IOException> {
	}
	writeAll(outputs : Map<Location, String>) : Option<IOException> {
	}
}

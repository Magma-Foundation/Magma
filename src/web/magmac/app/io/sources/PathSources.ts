import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { MapCollector } from "../../../../magmac/api/collect/map/MapCollector";
import { ResultCollector } from "../../../../magmac/api/iter/collect/ResultCollector";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { InlineIOResult } from "../../../../magmac/app/io/InlineIOResult";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { Files } from "../../../../java/nio/file/Files";
import { Path } from "../../../../java/nio/file/Path";
import { Map } from "../../../../magmac/api/collect/map/Map";
export class PathSources {
	readAll : IOResult<Map<Location, String>>{
	}
	apply : IOResult<Map<Location, String>>{
	}
}

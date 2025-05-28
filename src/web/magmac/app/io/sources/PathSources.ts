import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { MapCollector } from "../../../../magmac/api/collect/map/MapCollector";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { ResultCollector } from "../../../../magmac/api/iter/collect/ResultCollector";
import { Result } from "../../../../magmac/api/result/Result";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { InlineIOResult } from "../../../../magmac/app/io/InlineIOResult";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { IOException } from "../../../../java/io/IOException";
import { Files } from "../../../../java/nio/file/Files";
import { Path } from "../../../../java/nio/file/Path";
export class PathSources {
	readAll() : IOResult<Map<Location, String>>;
	apply(sources : Iter<Path>) : IOResult<Map<Location, String>>;
	getCollect(sources : Iter<Path>) : Result<Map<Location, String>, IOException>;
	getTuple2IOResult(source : PathSource) : IOResult<Tuple2<Location, String>>;
	getCollected(sources : Iter<Path>) : List<PathSource>;
}

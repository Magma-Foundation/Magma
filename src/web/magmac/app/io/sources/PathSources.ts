import { List } from "../../../../magmac/api/collect/list/List";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { ResultCollector } from "../../../../magmac/api/iter/collect/ResultCollector";
import { Result } from "../../../../magmac/api/result/Result";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { InlineIOResult } from "../../../../magmac/app/io/InlineIOResult";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { SimpleUnit } from "../../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
import { IOException } from "../../../../java/io/IOException";
import { Files } from "../../../../java/nio/file/Files";
import { Path } from "../../../../java/nio/file/Path";
export class PathSources {
	getTuple2IOResult(source : PathSource) : IOResult<Unit<String>> {return 0.read( ).mapValue( 0);;}
	readAll() : IOResult<UnitSet<String>> {return 0.walk( 0.root).flatMapValue( 0.apply);;}
	apply(sources : Iter<Path>) : IOResult<UnitSet<String>> {return new InlineIOResult<>( 0.getCollect( 0));;}
	getCollect(sources : Iter<Path>) : Result<UnitSet<String>, IOException> {return 0.getCollected( 0).iter( ).map( 0.getTuple2IOResult).map( 0.result).collect( new ResultCollector<>( new UnitSetCollector<>( )));;}
	getCollected(sources : Iter<Path>) : List<PathSource> {return 0.filter( 0.isRegularFile).filter( 0).map( 0).collect( new ListCollector<>( ));;}
}

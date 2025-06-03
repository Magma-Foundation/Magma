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
	private static getTuple2IOResult( source : PathSource) : IOResult<Unit<String>> {return source.read( ).mapValue( 0);;}
	public readAll() : IOResult<UnitSet<String>> {return SafeFiles.walk( this.root).flatMapValue( this.apply);;}
	private apply( sources : Iter<Path>) : IOResult<UnitSet<String>> {return new InlineIOResult<>( this.getCollect( sources));;}
	private getCollect( sources : Iter<Path>) : Result<UnitSet<String>, IOException> {return this.getCollected( sources).iter( ).map( PathSources.getTuple2IOResult).map( IOResult.result).collect( new ResultCollector<>( new UnitSetCollector<>( )));;}
	private getCollected( sources : Iter<Path>) : List<PathSource> {return sources.filter( Files.isRegularFile).filter( 0).map( 0).collect( new ListCollector<>( ));;}
}

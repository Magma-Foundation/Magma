import { List } from "../../../../magmac/api/collect/list/List";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { ResultCollector } from "../../../../magmac/api/iter/collect/ResultCollector";
import { Result } from "../../../../magmac/api/result/Result";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { InlineIOResult } from "../../../../magmac/app/io/InlineIOResult";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { SimpleUnit } from "../../../../magmac/app/stage/SimpleUnit";
import { Unit } from "../../../../magmac/app/stage/Unit";
import { UnitSet } from "../../../../magmac/app/stage/UnitSet";
import { IOException } from "../../../../java/io/IOException";
import { Files } from "../../../../java/nio/file/Files";
import { Path } from "../../../../java/nio/file/Path";
export class PathSources {
	private static getTuple2IOResult( source : PathSource) : IOResult<Unit<String>> {
		return source.read( ).mapValue( ( input : String) => new SimpleUnit<>( source.computeLocation( ), input));
	}
	public readAll() : IOResult<UnitSet<String>> {
		return SafeFiles.walk( this.root).flatMapValue( ( sources : Iter<Path>) => this.apply( sources));
	}
	private apply( sources : Iter<Path>) : IOResult<UnitSet<String>> {
		return new InlineIOResult<>( this.getCollect( sources));
	}
	private getCollect( sources : Iter<Path>) : Result<UnitSet<String>, IOException> {
		return this.getCollected( sources).iter( ).map( ( source : PathSource) => PathSources.getTuple2IOResult( source)).map( ( tuple2IOResult : IOResult<Unit<String>>) => tuple2IOResult.result( )).collect( new ResultCollector<>( new UnitSetCollector<>( )));
	}
	private getCollected( sources : Iter<Path>) : List<PathSource> {
		return sources.filter( ( path1 : Path) => Files.isRegularFile( path1)).filter( ( file : Path) => file.toString( ).endsWith( ".java")).map( ( path : Path) => new PathSource( this.root, path)).collect( new ListCollector<>( ));
	}
}

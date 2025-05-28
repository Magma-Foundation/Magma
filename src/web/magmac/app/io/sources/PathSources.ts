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
	readAll() : IOResult<Map<Location, String>> {
		return SafeFiles.walk( this.root).flatMapValue( (Iter<Path> sources)  => this.apply( sources));
	}
	apply(sources : Iter<Path>) : IOResult<Map<Location, String>> {
		return new InlineIOResult<>( this.getCollect( sources));
	}
	getCollect(sources : Iter<Path>) : Result<Map<Location, String>, IOException> {
		return this.getCollected( sources).iter( ).map( (PathSource source)  => this.getTuple2IOResult( source)).map( (IOResult<Tuple2<Location, String>> tuple2IOResult)  => tuple2IOResult.result( )).collect( new ResultCollector<>( new MapCollector<>( )));
	}
	getTuple2IOResult(source : PathSource) : IOResult<Tuple2<Location, String>> {
		return source.read( ).mapValue( (String input)  => new Tuple2<>( source.computeLocation( ), input));
	}
	getCollected(sources : Iter<Path>) : List<PathSource> {
		return sources.filter( (Path path1)  => Files.isRegularFile( path1)).filter( (Path file)  => file.toString( ).endsWith( ".java")).map( (Path path)  => new PathSource( this.root, path)).collect( new ListCollector<>( ));
	}
}

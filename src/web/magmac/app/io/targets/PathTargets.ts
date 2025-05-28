import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { IOException } from "../../../../java/io/IOException";
import { Files } from "../../../../java/nio/file/Files";
import { Path } from "../../../../java/nio/file/Path";
export class PathTargets {
	write(location : Location, output : String) : Option<IOException> {
		 Path targetParent=location.namespace( ).iter( ).fold( this.root( ),  (Path path, String other) ->path.resolve( other));
		if(!Files.exists( targetParent)){ 
		 Option<IOException> maybeError=SafeFiles.createDirectories( targetParent);
		if(maybeError.isPresent( )){ 
		return maybeError;}}
		 Path target=targetParent.resolve( location.name( )+"."+this.extension);
		return SafeFiles.writeString( target, output);
	}
	writeAll(outputs : Map<Location, String>) : Option<IOException> {
		return outputs.iterEntries( ).map( (Tuple2<Location, String> entry) ->this.write( entry.left( ), entry.right( ))).flatMap( (Option<IOException> option) ->Iters.fromOption( option)).next( );
	}
}

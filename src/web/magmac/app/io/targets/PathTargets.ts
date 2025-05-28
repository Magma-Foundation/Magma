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
	public writeAll( outputs : UnitSet<String>) : Option<IOException> {
		return outputs.iter( ).map( ( entry : Unit<String>) => this.write( entry)).flatMap( ( option : Option<IOException>) => Iters.fromOption( option)).next( );
	}
	private write( entry : Unit<String>) : Option<IOException> {
		return entry.deconstruct( ( location : Location,  output : String) => {
		 targetParent : Path=location.namespace( ).iter( ).fold( this.root, ( path : Path,  other : String) => path.resolve( other));
		if(!Files.exists( targetParent)){ 
		 maybeError : Option<IOException>=SafeFiles.createDirectories( targetParent);
		if(maybeError.isPresent( )){ 
		return maybeError;}}
		 target : Path=targetParent.resolve( location.name( )+"."+this.extension);
		return SafeFiles.writeString( target, output);});
	}
}

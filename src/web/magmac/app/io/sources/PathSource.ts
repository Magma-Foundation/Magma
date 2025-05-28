import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { Path } from "../../../../java/nio/file/Path";
export class PathSource {
	computeName() : String {
		fileName : String=this.child.getFileName( ).toString( );
		fileSeparator : int=fileName.lastIndexOf( '.');
		return fileName.substring( 0, fileSeparator);
	}
	read() : IOResult<String> {
		return SafeFiles.readString( this.child);
	}
	computeLocation() : Location {
		return new Location( this.computeNamespace( ), this.computeName( ));
	}
	computeNamespace() : List<String> {
		segments : List<String>=Lists.empty( );
		relative : Path=this.root.relativize( this.child).getParent( );
		if(null==relative){ 
		return Lists.empty( );}
		nameCount : int=relative.getNameCount( );
		i : int=0;
		if(i<nameCount){ 
		segments=segments.add( relative.getName( i).toString( ));
		i++;}
		return segments;
	}
}

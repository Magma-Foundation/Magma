import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { Path } from "../../../../java/nio/file/Path";
export class PathSource {
	computeName() : String {
		 String fileName=this.child.getFileName( ).toString( );
		 int fileSeparator=fileName.lastIndexOf( '.');
		return fileName.substring( 0, fileSeparator);
	}
	read() : IOResult<String> {
		return SafeFiles.readString( this.child);
	}
	computeLocation() : Location {
		return new Location( this.computeNamespace( ), this.computeName( ));
	}
	computeNamespace() : List<String> {
		 List<String> segments=Lists.empty( );
		 Path relative=this.root.relativize( this.child).getParent( );
		if(null==relative){ 
		return Lists.empty( );}
		 int nameCount=relative.getNameCount( );
		 int i=0;
		if(i<nameCount){ 
		segments=segments.add( relative.getName( i).toString( ));
		i++;}
		return segments;
	}
}

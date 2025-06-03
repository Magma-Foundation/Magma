import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { Path } from "../../../../java/nio/file/Path";
export class PathSource {
	public computeName() : String { let fileName : var=this.child.getFileName( ).toString( ); let fileSeparator : var=fileName.lastIndexOf( '.');return fileName.substring( 0, fileSeparator);;}
	public read() : IOResult<String> {return SafeFiles.readString( this.child);;}
	public computeLocation() : Location {return new Location( this.computeNamespace( ), this.computeName( ));;}
	private computeNamespace() : List<String> { let segments : List<String>=Lists.empty( ); let relative : var=this.root.relativize( this.child).getParent( );if(true){ return Lists.empty( );;} let nameCount : var=relative.getNameCount( ); let i : var=0;if(true){ segments=segments.addLast( relative.getName( i).toString( ));i++;;}return segments;;}
}

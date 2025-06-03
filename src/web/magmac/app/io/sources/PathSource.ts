import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { Path } from "../../../../java/nio/file/Path";
export class PathSource {
	computeName() : String {fileName : var=this.child.getFileName( ).toString( );fileSeparator : var=fileName.lastIndexOf( '.');return fileName.substring( 0, fileSeparator);;}
	read() : IOResult<String> {return SafeFiles.readString( this.child);;}
	computeLocation() : Location {return new Location( this.computeNamespace( ), this.computeName( ));;}
	computeNamespace() : List<String> {segments : List<String>=Lists.empty( );relative : var=this.root.relativize( this.child).getParent( );if(true){ return Lists.empty( );;}nameCount : var=relative.getNameCount( );i : var=0;if(true){ segments=segments.addLast( relative.getName( i).toString( ));i++;;}return segments;;}
}

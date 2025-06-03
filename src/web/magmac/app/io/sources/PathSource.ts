import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { IOResult } from "../../../../magmac/app/io/IOResult";
import { Location } from "../../../../magmac/app/io/Location";
import { SafeFiles } from "../../../../magmac/app/io/SafeFiles";
import { Path } from "../../../../java/nio/file/Path";
export class PathSource {
	computeName() : String {break;break;return fileName.substring( 0, fileSeparator);;}
	read() : IOResult<String> {return SafeFiles.readString( this.child);;}
	computeLocation() : Location {return new Location( this.computeNamespace( ), this.computeName( ));;}
	computeNamespace() : List<String> {break;break;if(true){ return Lists.empty( );;}break;break;if(true){ break;i++;;}return segments;;}
}

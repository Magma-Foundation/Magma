import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { JVMList } from "../../../magmac/api/collect/list/JVMList";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Err } from "../../../magmac/api/result/Err";
import { Ok } from "../../../magmac/api/result/Ok";
import { IOException } from "../../../java/io/IOException";
import { Files } from "../../../java/nio/file/Files";
import { Path } from "../../../java/nio/file/Path";
import { Collectors } from "../../../java/util/stream/Collectors";
import { Stream } from "../../../java/util/stream/Stream";
export class SafeFiles {
	writeString(target : Path, output : String) : Option<IOException> {if(true){ 0.writeString( 0, 0);return new None<>( );;}if(true){ return new Some<>( 0);;};}
	walk(sourceDirectory : Path) : IOResult<Iter<Path>> {if(true){ break;return new InlineIOResult<>( new Ok<>( new JVMList<>( 0.collect( 0.toList( ))).iter( )));;}if(true){ return new InlineIOResult<>( new Err<>( 0));;};}
	readString(source : Path) : IOResult<String> {if(true){ return new InlineIOResult<>( new Ok<>( 0.readString( 0)));;}if(true){ return new InlineIOResult<>( new Err<>( 0));;};}
	createDirectories(targetParent : Path) : Option<IOException> {if(true){ 0.createDirectories( 0);return new None<>( );;}if(true){ return new Some<>( 0);;};}
}

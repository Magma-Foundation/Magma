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
	writeString(target : Path, output : String) : Option<IOException> {if(true){ Files.writeString( target, output);return new None<>( );;}if(true){ return new Some<>( e);;};}
	walk(sourceDirectory : Path) : IOResult<Iter<Path>> {if(true){ stream : Stream<Path>=Files.walk( sourceDirectory);return new InlineIOResult<>( new Ok<>( new JVMList<>( stream.collect( Collectors.toList( ))).iter( )));;}if(true){ return new InlineIOResult<>( new Err<>( e));;};}
	readString(source : Path) : IOResult<String> {if(true){ return new InlineIOResult<>( new Ok<>( Files.readString( source)));;}if(true){ return new InlineIOResult<>( new Err<>( e));;};}
	createDirectories(targetParent : Path) : Option<IOException> {if(true){ Files.createDirectories( targetParent);return new None<>( );;}if(true){ return new Some<>( e);;};}
}

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
	public static writeString( target : Path,  output : String) : Option<IOException> {
		try{ 
		Files.writeString( target, output);
		return new None<>( );}
		catch( e : IOException){ 
		return new Some<>( e);}
	}
	public static walk( sourceDirectory : Path) : IOResult<Iter<Path>> {
		try{ 
		 stream : Stream<Path>=Files.walk( sourceDirectory);
		return new InlineIOResult<>( new Ok<>( new JVMList<>( stream.collect( Collectors.toList( ))).iter( )));}
		catch( e : IOException){ 
		return new InlineIOResult<>( new Err<>( e));}
	}
	public static readString( source : Path) : IOResult<String> {
		try{ 
		return new InlineIOResult<>( new Ok<>( Files.readString( source)));}
		catch( e : IOException){ 
		return new InlineIOResult<>( new Err<>( e));}
	}
	public static createDirectories( targetParent : Path) : Option<IOException> {
		try{ 
		Files.createDirectories( targetParent);
		return new None<>( );}
		catch( e : IOException){ 
		return new Some<>( e);}
	}
}

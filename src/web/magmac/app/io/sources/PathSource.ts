export class PathSource {
	public computeName() : String { let fileName : var=this.child.getFileName( ).toString( ); let fileSeparator : var=fileName.lastIndexOf( '.');return fileName.substring( 0, fileSeparator);;}
	public read() : IOResult<String> {return SafeFiles.readString( this.child);;}
	public computeLocation() : Location {return new Location( this.computeNamespace( ), this.computeName( ));;}
	private computeNamespace() : List<String> { let segments : List<String>=Lists.empty( ); let relative : var=this.root.relativize( this.child).getParent( );if(true){ return Lists.empty( );;} let nameCount : var=relative.getNameCount( ); let i : var=0;if(true){ segments=segments.addLast( relative.getName( i).toString( ));i++;;}return segments;;}
}

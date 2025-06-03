export class ThrowableError {
	public display() : String { let writer : var=new StringWriter( );this.throwable.printStackTrace( new PrintWriter( writer));return writer.toString( );;}
}

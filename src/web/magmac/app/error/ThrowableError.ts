import { Error } from "../../../magmac/api/error/Error";
import { PrintWriter } from "../../../java/io/PrintWriter";
import { StringWriter } from "../../../java/io/StringWriter";
export class ThrowableError {
	public display() : String { let writer : var=new StringWriter( );this.throwable.printStackTrace( new PrintWriter( writer));return writer.toString( );;}
}

import { Error } from "../../../magmac/api/error/Error";
import { PrintWriter } from "../../../java/io/PrintWriter";
import { StringWriter } from "../../../java/io/StringWriter";
export class ThrowableError {
	display() : String { StringWriter writer=new StringWriter( );this.throwable.printStackTrace( new PrintWriter( writer));return writer.toString( );}
}

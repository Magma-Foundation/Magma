import { Error } from "../../../magmac/api/error/Error";
export class ApplicationError {
	public display() : String {
		return this.error.display( );
	}
}

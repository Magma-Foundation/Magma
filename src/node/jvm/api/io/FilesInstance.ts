// []
import { Path } from "../../../magma/api/io/Path";
export interface FilesInstance {
	static get(first: string, ...more: string[]): Path;
}

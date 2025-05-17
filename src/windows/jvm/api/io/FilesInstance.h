import { Path } from "magma/api/io/Path";
export interface FilesInstance {
	mut get(first: &[I8], ...more: &[I8][]): Path;

}

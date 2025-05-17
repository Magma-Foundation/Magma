#include "./Files.h"
export interface FilesInstance {
	mut get(first: &[I8], ...more: &[I8][]): Path;

}

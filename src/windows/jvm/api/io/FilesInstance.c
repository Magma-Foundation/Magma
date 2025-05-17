#include "./Files.h"
export interface FilesInstance {
	get(first: &[I8], ...more: &[I8][]): Path;

}

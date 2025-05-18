#include "./Files.h"
export interface FilesInstance {
	static Path get(&[I8] first, ...&[I8][] more);
}

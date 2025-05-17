#include "./Lists.h"
export interface ListsInstance {
	mut fromArray<T>(elements: T[]): List<T>;

	mut empty<T>(): List<T>;

	mut of<T>(...elements: T[]): List<T>;

}

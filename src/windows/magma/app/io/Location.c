#include "./Location.h"
export class Location {
	namespace: List<&[I8]>;
	name: &[I8];
	constructor (namespace: List<&[I8]>, name: &[I8]) {
		this.namespace = namespace;
		this.name = name;
	}
}

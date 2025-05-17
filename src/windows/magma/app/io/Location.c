#include "./Location.h"
export class Location {
	List<&[I8]> namespace;
	&[I8] name;
	constructor (List<&[I8]> namespace, &[I8] name) {
		this.namespace = namespace;
		this.name = name;
	}
}

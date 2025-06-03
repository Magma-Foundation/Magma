export interface Source {
	 computeName() : String;
	 read() : IOResult<String>;
	 computeLocation() : Location;
}

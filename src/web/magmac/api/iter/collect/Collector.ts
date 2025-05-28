export interface Collector {
	 createInitial() : C;
	 fold( current : C,  element : T) : C;
}

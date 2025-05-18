/*[
	Actual, 
	Characters, 
	Collector, 
	Console, 
	Files, 
	Lists, 
	Namespace, 
	Strings
]*/
export interface Collector<T, C> {
	createInitial(): C;
	fold(current: C, element: T): C;
}

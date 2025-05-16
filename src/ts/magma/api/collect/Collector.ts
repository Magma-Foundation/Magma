/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate
]*/
export interface Collector<T, C> {
	createInitial(): C;
	fold(current: C, element: T): C;
}

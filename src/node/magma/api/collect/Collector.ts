// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector]
export interface Collector<T, C> {
	createInitial(): C;
	fold(current: C, element: T): C;
}

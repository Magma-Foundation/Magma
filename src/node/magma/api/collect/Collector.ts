// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector]
export interface Collector<T, C> {
	createInitial(): C;
	fold(current: C, element: T): C;
}

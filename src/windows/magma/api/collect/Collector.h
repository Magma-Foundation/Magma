export interface Collector<T, C> {
	mut createInitial(): C;
	mut fold(mut current: C, mut element: T): C;
}

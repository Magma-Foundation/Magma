#include "./Collector.h"
export interface Collector<T, C> {
	createInitial(): C;
	fold(current: C, element: T): C;
}

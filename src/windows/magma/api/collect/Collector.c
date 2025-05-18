#include "./Collector.h"
export interface Collector<T, C> {
	C createInitial();
	C fold(C current, T element);
}

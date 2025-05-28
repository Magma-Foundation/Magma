export interface Collector { createInitial()() : C;C fold(C current, element)() : T;
}

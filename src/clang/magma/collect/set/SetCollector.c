#include "SetCollector.h"
// expand Set__T = Set_<struct T>
// expand Set__T = Set_<struct T>
// expand Set__T = Set_<struct T>
struct Set__T createInitial(){return Sets.empty();}struct Set__T fold(struct Set__T tSet, struct T t){return tSet.add(t);}
#include "EmptyHead.h"
// expand Option_T = Option<struct T>
struct Option_T next(){return new None<>();}
#include "LastLocator.h"
// expand Option_Integer = Option<struct Integer>
struct Option_Integer locate(struct String input, struct String infix){int index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<Integer>(index);}
#include "LastLocator.h"
magma.option.Option<int> locate(String input, String infix){int index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<Integer>(index);
}


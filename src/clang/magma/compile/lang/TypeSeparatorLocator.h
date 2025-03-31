#ifndef magma_compile_lang_TypeSeparatorLocator
#define magma_compile_lang_TypeSeparatorLocator
#include "../../../magma/compile/rule/locate/Locator.h"
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct TypeSeparatorLocator{
};
magma.option.Option<int> locate(String input, String infix);
// expand magma.option.Option<int>
// expand magma.option.None<>
#endif


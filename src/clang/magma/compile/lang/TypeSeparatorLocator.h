#ifndef magma_compile_lang_TypeSeparatorLocator
#define magma_compile_lang_TypeSeparatorLocator
#include "../../../magma/compile/rule/locate/Locator.h"
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct TypeSeparatorLocator{};
// expand Option_Integer = Option<struct Integer>
struct Option_Integer locate(struct String input, struct String infix);
#endif

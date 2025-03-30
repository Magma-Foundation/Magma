#ifndef magma_compile_lang_InvocationStartLocator
#define magma_compile_lang_InvocationStartLocator
#include "../../../magma/compile/rule/locate/Locator.h"
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct InvocationStartLocator{
};
// expand Option_Integer = Option<struct Integer>
// expand None_ = None<struct >
struct Option_Integer locate(struct String input, struct String infix);
#endif


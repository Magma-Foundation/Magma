#include "OrRule.h"
struct Result_T_CompileError apply(struct Result_T_CompileError(*applicator)(struct Rule), struct Context(*context)()){return rules.stream().foldWithInitial(OrState_T(), __lambda0__).toResult().match(Ok.new, __lambda1__);
}
struct Result_Node_CompileError parse(struct String input){return apply(__lambda2__, __lambda3__);
}
struct Result_String_CompileError generate(struct Node input){return apply(__lambda4__, __lambda5__);
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();


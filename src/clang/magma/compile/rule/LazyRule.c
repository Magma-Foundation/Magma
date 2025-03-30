#include "LazyRule.h"
struct Result_Node_CompileError parse(struct String input){return withChildSet(__lambda0__, StringContext(input));
}
struct Result_String_CompileError generate(struct Node node){return withChildSet(__lambda1__, NodeContext(node));
}
struct Result_T_CompileError withChildSet(struct Result_T_CompileError(*mapper)(struct Rule), struct Context context){return child.map(mapper).orElseGet(__lambda2__);
}
struct void set(struct Rule child){this.child = new Some<>(child);
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();


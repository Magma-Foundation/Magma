#include "FilterRule.h"
struct public FilterRule(struct Filter filter, struct Rule rule){this.rule = rule;
        this.filter = filter;
}
struct Result_Node_CompileError parse(struct String input){if (filter.isValid(input)) {
            return rule.parse(input);
        }else {
            return new Err<>(new CompileError("Not a symbol", new StringContext(input)));
        }
}
struct Result_String_CompileError generate(struct Node node){return rule.generate(node);
}


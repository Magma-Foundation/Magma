#include "FilterRule.h"
public FilterRule(Filter filter, Rule rule){this.rule = rule;
        this.filter = filter;
}
Result<Node, CompileError> parse(String input){if (filter.isValid(input)) {
            return rule.parse(input);
        }else {
            return new Err<>(new CompileError("Not a symbol", new StringContext(input)));
        }
}
Result<String, CompileError> generate(Node node){return rule.generate(node);
}

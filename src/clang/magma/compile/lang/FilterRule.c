#include "FilterRule.h"
magma.compile.lang.public FilterRule(magma.compile.lang.Filter filter, magma.compile.rule.Rule rule){this.rule = rule;
        this.filter = filter;
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){if (filter.isValid(input)) {
            return rule.parse(input);
        }else {
            return new Err<>(new CompileError("Not a symbol", new StringContext(input)));
        }
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return rule.generate(node);
}

#include "SymbolRule.h"
Result<struct Node, struct CompileError> parse(struct String input}{if (isSymbol(input)) {
            return rule.parse(input);
        }else {
            return new Err<>(new CompileError(, new StringContext(input)));
        }}int isSymbol(struct String input}{for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c ==  || Character.isLetter(c) || (i != 0 && Character.isDigit(c))) continue;
            return false;
        }return true;}Result<struct String, struct CompileError> generate(struct Node node}{return rule.generate(node);}
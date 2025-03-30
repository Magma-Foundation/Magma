#include "SymbolFilter.h"
int isValid(struct String input){for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '_' || Character.isLetter(c) || (i != 0 && Character.isDigit(c))) continue;
            return false;
        }return true;
}


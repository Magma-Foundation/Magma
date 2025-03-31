#include "SymbolFilter.h"
int isValid(String input){if(input.isEmpty()) return false;for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '_' || Character.isLetter(c) || (i != 0 && Character.isDigit(c))) continue;
            return false;
        }return true;
}

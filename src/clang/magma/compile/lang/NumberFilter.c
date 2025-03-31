#include "NumberFilter.h"
magma.compile.lang.boolean isValid(String input){for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) continue;
            return false;
        }return true;
}


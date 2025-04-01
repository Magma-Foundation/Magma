#include "TypeSeparatorLocator.h"
Option<int> locate(String input, String infix){int depth = 0;for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) return new Some<>(i);
            if (c == '>') depth++;
            if (c == '<') depth--;
        }return ();
}

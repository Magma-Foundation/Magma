#include "ValueFolder.h"
DividingState fold(DividingState current, char c){if (c == ',' && current.isLevel()) return current.advance();

        DividingState appended = current.append(c);if(c == '-') {
            boolean isArrow = current.peek().filter(inner -> inner == '>').isPresent();
            if(isArrow) {
                return appended.appendAndDiscard().orElse(appended);
            }
        }

        if (c == '<' || c == '(') return appended.enter();
        if (c == '>' || c == ')') return appended.exit();return appended;
}
String join(String current, String element){if (current.isEmpty()) return element;return current+", "+element;
}

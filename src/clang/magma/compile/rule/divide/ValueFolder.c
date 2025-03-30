#include "ValueFolder.h"
struct DividingState fold(struct DividingState current, struct char c}{if (c ==  && current.isLevel()) return current.advance();

        DividingState appended = current.append(c);
        if (c == ) return appended.enter();
        if (c == ) return appended.exit();return appended;}struct String join(struct String current, struct String element}{if (current.isEmpty()) return element;return current +  + element;}
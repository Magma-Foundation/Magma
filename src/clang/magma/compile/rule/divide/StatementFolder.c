#include "StatementFolder.h"
struct DividingState fold(struct DividingState current, struct char c){DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '}' && appended.isShallow()) return appended.advance().exit();
        if (c == '{' || c == '(') return appended.enter();
        if (c == '}' || c == ')') return appended.exit();return appended;
}
struct String join(struct String current, struct String element){return current+element;
}


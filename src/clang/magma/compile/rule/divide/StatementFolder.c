#include "StatementFolder.h"
magma.compile.rule.divide.DividingState fold(magma.compile.rule.divide.DividingState current, char c){DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '}' && appended.isShallow()) return appended.advance().exit();
        if (c == '{' || c == '(') return appended.enter();
        if (c == '}' || c == ')') return appended.exit();return appended;
}
String join(String current, String element){return current+element;
}

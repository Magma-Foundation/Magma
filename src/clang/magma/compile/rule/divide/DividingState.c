#include "DividingState.h"
DividingState append(char c);
int isLevel();
DividingState exit();
DividingState enter();
DividingState advance();
List_<String> segments();
int isShallow();
Option<Tuple<char, DividingState>> append();
Option<DividingState> appendAndDiscard();
Option<Tuple<char, DividingState>> pop();
Option<char> peek();

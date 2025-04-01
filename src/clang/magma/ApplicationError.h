#ifndef magma_ApplicationError
#define magma_ApplicationError
#include "../magma/error/Error.h"
struct ApplicationError{Error error;
};
public ApplicationError(Error error);
String display();
#endif

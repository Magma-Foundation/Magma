#ifndef magma_ApplicationError
#define magma_ApplicationError
#include "../magma/error/Error.h"
struct ApplicationError{magma.error.Error error;
};
magma.public ApplicationError(magma.error.Error error);
String display();
#endif


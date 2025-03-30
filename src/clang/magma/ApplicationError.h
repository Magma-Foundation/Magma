#ifndef magma_ApplicationError
#define magma_ApplicationError
#include "../magma/error/Error.h"
struct ApplicationError{struct Error error;
};
struct public ApplicationError(struct Error error);
struct String display();
#endif


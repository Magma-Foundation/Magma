#include "ApplicationError.h"
magma.public ApplicationError(magma.error.Error error){this.error = error;
}
String display(){return error.display();
}


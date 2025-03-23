struct Error_VTable {
    void (*display)(String);
    void (*drop)(void* self);
}

struct Error {
    void* self;
    Error_VTable vtable;
    void (*drop)(void* self);
};

void drop_Error(void* self) {
    struct Error this = (struct Error*) self;

    this.drop(this.self);
    free(this.self);
}

struct Error Error(void* self, Error_VTable vtable) {
    struct Error this;
    this.self = self;
    this.vtable = vtable;
    return this;
}
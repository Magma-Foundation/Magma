extern import stdlib;

class def Array[T](length : Size) => {
    const block = malloc(length * T.size);

    callable def get(index : Size) => *(block + index);
    callable def set(index : Size, value : T) => *(block + index) = value;

    def drop() => free(block);
}
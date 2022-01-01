import native "string";

native def strlen(value : Ref[I8]) : Size;

struct IndexError {
    index : Size;
    length : Size;
}

trait String {
    length : Size;

    callable apply : Size => I8 ? IndexError;
}

out def StringConstructor(value : Ref[I8]) => [String](value){
    length : strlen(value),
    apply : index => index < length
        ? *(value + index)
        : throw [IndexError]{ index, length }
};

import native "stdlib";

native def malloc(size : Size) : Ref[Any];

native def free(block : Ref[Any]) : Void;

out def StringDeconstructor(value : String) : Ref[I8] => {
    const { length } = value;
    const block = malloc(length * I8.size);

    for(let i = 0; i < length; i++) {
        let element =
            try value(i)
            catch(error : IndexError) '?';

        *(block + i) = element;
    }

    return block;
}

import native "stdio";

native def printf(format : String, args : Any...) : Void;

def print(value : String) => {
    const copy = StringDeconstructor(value);
    printf("%s", copy);
    free(copy);
}

def describe(name : String, action : () => Void) => {
    print(name + '\n');
}

let testResult = true;

def it(name : String, action : () => Void) => {
    testResult = true;
    action();

    const suffix = testResult ? "Pass" : "Fail";
    print('\t' + name + ": " + suffix);
}

def assertTrue(value : Bool) => {
    if(!value) testResult = false;
}

def main() : I16 => {
    describe("The application", () => {
        it("should", () => {
            assertTrue(true);
        });
    });
    return 0;
}
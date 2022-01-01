struct String {
}

out def StringConstructor(value : Ref[I8]) : String => {
    return [String]{};
}

import native "stdio";

native def printf(format : String, args : Any...) : Void;

def print(value : String) => {
    printf("%s", value);
}

def it(name : String, action : () => Void) => {
    print('\t' + name);
}

def describe(name : String, action : () => Void) => {
    print(name + '\n');
}

def main() : I16 => {
    describe("The application", () => {
        it("should", () => {

        });
    });
    return 0;
}
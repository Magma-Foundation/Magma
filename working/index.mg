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
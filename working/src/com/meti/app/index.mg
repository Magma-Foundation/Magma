extern import stdio;

let __testResult__ : I16 = true;

def it(message : &I8, action : () => Void) => {
    __testResult__ = true;
    action();

    printf(" - %s -> ", message);

    let status : &I8;
    if(__testResult__) {
        status = "Passing";
    } else {
        status = "Failing";
    }

    printf("%s\n", status);
}

def describe(name : &I8, action : () => Void) => {
    printf("%s\n", name);
    action();
}

def assertTrue(value : Bool) => {
    if(! value) __testResult__ = false;
}

def assertFalse(value : Bool) => {
    if(value) __testResult__ = false;
}

def assertI16Equals(expected : I16, actual : I16) => {
    if(expected != actual) __testResult__ = false;
}

def main() => {
    describe("The assertions", () => {
        it("should assert a state to be true", () => {
            assertTrue(true);
        });

        it("should assert a state to be false", () => {
            assertFalse(false);
        });

        it("should assert that two signed 16 bit integers are equal", () => {
            assertI16Equals(100, 100);
        });
    });
    return 0;
}
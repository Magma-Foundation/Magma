extern import stdio;

let result : I16 = true;

def it(message : &I8, action : () => Void) => {
    action();
    printf(" - %s -> ", message);
    let status : &I8;
    if(result) {
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
    if(! value) result = false;
}

def assertFalse(value : Bool) => {
    if(value) result = true;
}

def main() => {
    describe("The assertions test", () => {
        it("should assert a state to be true", () => {
            assertTrue(true);
        });

        it("should assert a state to be false", () => {
            assertFalse(false);
        });
    });
    return 0;
}
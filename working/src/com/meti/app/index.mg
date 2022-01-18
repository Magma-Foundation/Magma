extern import stdio;

let result : I16 = true;

def it(message : &I8, action : () => Void) => {
    action();
    printf("\t%s -> ", message);
    let status : &I8;
    if(result) {
        status = "Passing";
    } else {
        status = "Failing";
    }
    printf("%s", status);
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
        it("should assert things to be true.", () => {
            assertTrue(true);
        });
    });
    return 0;
}
extern import stdio;

let result : I16 = true;

def it(message : &I8, action : () => Void) => {
    action();
    printf("\t%s -> ", message);
    if(result) {
        printf("%s", "PASSING");
    } else {
        printf("%s", "FAILING");
    }
}

def describe(name : &I8, action : () => Void) => {
    printf("%s\n", name);
    action();
}

def assertTrue(value : Bool) => {
    if(! value) {
        result = false;
    }
}

def lambda1() => {
    assertTrue(true);
}

def lambda0() => {
    it("should assert things to be true.", lambda1);
}

def main() => {
    describe("The assertions", lambda0);
    return 0;
}
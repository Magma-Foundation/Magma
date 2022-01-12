extern import stdio;

def describe(name : &I8, action : () => Void) => {
    printf("%s\n", name);
}

def main() => {
    describe("The assertions");
    return 0;
}
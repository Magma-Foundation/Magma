extern import stdio;

def describe(name : &I8) => {
    printf("%s :\n", name);
}

def main() => {
    describe("test");
    return 0;
}
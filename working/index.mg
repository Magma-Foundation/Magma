struct String {
}

out def StringConstructor(value : Ref[I8]) : String => {
    return [String]{};
}

def it(name : String, action : () => Void) => {
}

def describe(name : String, action : () => Void) => {
}

def main() : I16 => {
    describe("The application", () => {
        it("should", () => {

        });
    });
    return 0;
}
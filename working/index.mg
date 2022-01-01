def describe(name : Ref[I8], action : () => Void) : Void => {
};

def lambda0() : Void => {
};

def main() : I16 => {
    describe("The application", lambda0);
    return 0;
}
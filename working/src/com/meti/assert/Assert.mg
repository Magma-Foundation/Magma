extern import stdio;

extern def printf(format : &I8, args : Any...) : Void;

import com.meti.String;

def assertTrue(value : Bool) : Void => {
    if(!value) {
        printf("Assertion failed!");
    }
}
extern import stdio;
import com.meti.String;

extern operator def !(state : Bool) : Bool;

def assertTrue(value : Bool) : Void => {
    if(! value) {
        printf("Assertion failed!");
    }
}

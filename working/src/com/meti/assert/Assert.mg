extern import stdio;
import com.meti.String;

def assertTrue(value : Bool) : Void => {
    if(!value) {
        printf("Assertion failed!");
    }
}

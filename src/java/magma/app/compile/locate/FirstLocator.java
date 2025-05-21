package magma.app.compile.locate;

public class FirstLocator implements Locator {
    @Override
    public int apply(String input1, String infix1) {
        return input1.indexOf(infix1);
    }
}

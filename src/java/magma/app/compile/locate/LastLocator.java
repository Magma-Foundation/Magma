package magma.app.compile.locate;

public class LastLocator implements Locator {
    @Override
    public int apply(String input1, String infix1) {
        return input1.lastIndexOf(infix1);
    }
}

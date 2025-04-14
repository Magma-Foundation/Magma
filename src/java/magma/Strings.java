package magma;

class Strings {
    public static Main.String_ fromSlice(String value) {
        return new Main.JavaString(value);
    }

    public static String toSlice(Main.String_ string) {
        return new String(string.asCharArray());
    }
}

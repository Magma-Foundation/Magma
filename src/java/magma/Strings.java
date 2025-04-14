package magma;

class Strings {
    public static final class JavaString implements Main.String_ {
        private final char[] value;

        public JavaString(String value) {
            this.value = value.toCharArray();
        }

        @Override
        public String toString() {
            return new String(this.value);
        }

        @Override
        public char[] asCharArray() {
            return this.value;
        }
    }

    public static Main.String_ fromSlice(String value) {
        return new JavaString(value);
    }

    public static String toSlice(Main.String_ string) {
        return new String(string.asCharArray());
    }

    public static Main.String_ emptyString() {
        return new JavaString("");
    }
}

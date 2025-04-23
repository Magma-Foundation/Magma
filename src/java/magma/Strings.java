package magma;

class Strings {
    public record JavaString(String slice) implements Main.String_ {
        @Override
        public Main.String_ concatChar(char c) {
            return new JavaString(this.slice + c);
        }

        @Override
        public String toSlice() {
            return this.slice;
        }
    }

    public static Main.String_ empty() {
        return new JavaString("");
    }
}

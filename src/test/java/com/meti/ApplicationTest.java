package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    @Test
    void test_main() throws ApplicationException {
        assertRun("def main() : I16 => {return 0;}", "int main(){return 0;}");
    }

    private void assertRun(String source, String target) throws ApplicationException {
        assertEquals(target, run(source));
    }

    private String run(String content) throws ApplicationException {
        if (content.startsWith("def ")) {
            var from = "def ".length();
            var separator = content.indexOf('(');
            var name = slice(content, from, separator);
            var typeSeparator = content.indexOf(':');
            var returnSeparator = content.indexOf("=>");
            var typeString = slice(content, typeSeparator + 1, returnSeparator);
            String typeToUse;
            if (typeString.equals("I16")) {
                typeToUse = "int";
            } else if (typeString.equals("U16")) {
                typeToUse = "unsigned int";
            } else {
                throw new ApplicationException("Unknown type: " + typeString);
            }
            return typeToUse + " " + name + "(){return 0;}";
        } else {
            throw new ApplicationException("Unknown content: " + content);
        }
    }

    private String slice(String content, int start, int end) {
        return content.substring(start, end).trim();
    }

    @Test
    void test_name() throws ApplicationException {
        assertRun("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }

    @Test
    void test_name_whitespace() throws ApplicationException {
        assertRun("def  test () : I16 => {return 0;}", "int test(){return 0;}");
    }

    @Test
    void test_type() throws ApplicationException {
        assertRun("def main() : U16 => {return 0;}", "unsigned int main(){return 0;}");
    }

    @Test
    void test_unknown_type() {
        assertThrows(ApplicationException.class, () -> run("def main() : test => {return 0;}"));
    }
}

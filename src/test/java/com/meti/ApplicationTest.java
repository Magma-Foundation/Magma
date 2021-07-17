package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    @Test
    void test_main() throws ApplicationException {
        assertEquals("int main(){return 0;}", run("def main() : I16 => {return 0;}"));
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
            if(typeString.equals("I16")){
                typeToUse = "int";
            } else {
                typeToUse = "unsigned int";
            }
            return typeToUse + " " + name + "(){return 0;}";
        } else {
            throw new ApplicationException();
        }
    }

    private String slice(String content, int start, int end) {
        return content.substring(start, end).trim();
    }

    @Test
    void test_name() throws ApplicationException {
        assertEquals("int test(){return 0;}", run("def test() : I16 => {return 0;}"));
    }

    @Test
    void test_name_whitespace() throws ApplicationException {
        assertEquals("int test(){return 0;}", run("def  test () : I16 => {return 0;}"));
    }

    @Test
    void test_type() throws ApplicationException {
        assertEquals("unsigned int main(){return 0;}", run("def main() : U16 => {return 0;}"));
    }
}

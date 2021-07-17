package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    @Test
    void test_main() throws ApplicationException {
        assertEquals("int main(){return 0;}", run("def main() : I16 => {return 0;}"));
    }

    private String run(String content) throws ApplicationException {
        if(content.startsWith("def ")) {
            var separator = content.indexOf('(');
            return "int " + content.substring("def ".length(), separator) + "(){return 0;}";
        } else {
            throw new ApplicationException();
        }
    }

    @Test
    void test_name() throws ApplicationException {
        assertEquals("int test(){return 0;}", run("def test() : I16 => {return 0;}"));
    }
}

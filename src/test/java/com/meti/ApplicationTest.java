package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    @Test
    void test_main() throws ApplicationException {
        assertRun("def main() : I16 => {return 0;}", "int main(){return 0;}");
    }

    private static void assertRun(String source, String target) throws ApplicationException {
        assertEquals(target, new Application().run(source));
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
        assertThrows(ApplicationException.class, () -> new Application().run("def main() : test => {return 0;}"));
    }
}


#include <org.junit.jupiter.api.h>
#include <org.junit.jupiter.api.h>
#include <java.io.h>
#include <java.nio.file.h>
#include <java.nio.file.h>
#include <java.nio.file.h>
#include <static org.junit.jupiter.api.Assertions.h>
#include <static org.junit.jupiter.api.Assertions.h>
function ApplicationTest(){
    public static final Path SOURCE = Paths.get(".", "ApplicationTest.java");
    public static final Path TARGET = Paths.get(".", "ApplicationTest.mgs");

    @Test
    void generatesSomething() throws IOException {
        Files.createFile(SOURCE);
        new Application(new SingleSourceSet(SOURCE), Paths.get("."), ".mgs").run();
        assertTrue(Files.exists(TARGET));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(SOURCE);
        Files.deleteIfExists(TARGET);
    }

    @Test
    void generatesNothing() throws IOException {
        new Application(new SingleSourceSet(SOURCE), Paths.get("."), ".mgs").run();
        assertFalse(Files.exists(TARGET));
    }
}
module.exports = {
	ApplicationTest
};
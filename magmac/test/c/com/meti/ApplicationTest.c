import { AfterEach } from org.junit.jupiter.apiimport { Test } from org.junit.jupiter.apiimport { IOException } from java.ioimport { Files } from java.nio.fileimport { Path } from java.nio.fileimport { Paths } from java.nio.fileimport { assertFalse } from static org.junit.jupiter.api.Assertionsimport { assertTrue } from static org.junit.jupiter.api.Assertionspublic class ApplicationTest {
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
#include "ApplicationTest.c"
void tearDown(struct ApplicationTest* __self__){
        Files.deleteIfExists(SOURCE);
        Files.deleteIfExists(TARGET);
    }
void generatesNothing(struct ApplicationTest* __self__){
        new Application(new SingleSourceSet(SOURCE), Paths.get("."), ".mgs").run();
        assertFalse(Files.exists(TARGET));
    }
struct ApplicationTest ApplicationTest(){
    public static final Path SOURCE = Paths.get(".", "ApplicationTest.java")
    public static final Path TARGET = Paths.get(".", "ApplicationTest.mgs")
	def generatesSomething() : Void => {
        Files.createFile(SOURCE);
        new Application(new SingleSourceSet(SOURCE), Paths.get("."), ".mgs").run();
        assertTrue(Files.exists(TARGET));
    }
	struct ApplicationTest this = {};
	return this;
}
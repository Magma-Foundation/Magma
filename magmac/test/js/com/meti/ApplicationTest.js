import { AfterEach }import { Test }import { IOException }import { Files }import { Path }import { Paths }import { assertFalse }import { assertTrue }from org.junit.jupiter.apifrom org.junit.jupiter.apifrom java.iofrom java.nio.filefrom java.nio.filefrom java.nio.filefrom static org.junit.jupiter.api.Assertionsfrom static org.junit.jupiter.api.Assertions
function ApplicationTest(){public static final Path SOURCE = Paths.get(".", "ApplicationTest.java")
    public static final Path TARGET = Paths.get(".", "ApplicationTest.mgs")
	def generatesSomething() : Void => {
        Files.createFile(SOURCE);
        new Application(new SingleSourceSet(SOURCE), Paths.get("."), ".mgs").run();
        assertTrue(Files.exists(TARGET));
    }
	def tearDown() : Void => {
        Files.deleteIfExists(SOURCE);
        Files.deleteIfExists(TARGET);
    }
	def generatesNothing() : Void => {
        new Application(new SingleSourceSet(SOURCE), Paths.get("."), ".mgs").run();
        assertFalse(Files.exists(TARGET));
    }
	return {};
}
module.exports = {
	ApplicationTest
};
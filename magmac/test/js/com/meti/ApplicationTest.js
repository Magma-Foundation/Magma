const { AfterEach } = require("org.junit.jupiter.api");
const { Test } = require("org.junit.jupiter.api");
const { IOException } = require("java.io");
const { Files } = require("java.nio.file");
const { Path } = require("java.nio.file");
const { Paths } = require("java.nio.file");
const { assertFalse } = require("static org.junit.jupiter.api.Assertions");
const { assertTrue } = require("static org.junit.jupiter.api.Assertions");
function ApplicationTest(){
    public static final Path SOURCE = Paths.get(".", "ApplicationTest.java")
    public static final Path TARGET = Paths.get(".", "ApplicationTest.mgs")
	def generatesSomething() : Void => {
        Files.createFile(SOURCE);
        new Application(new SingleSourceSet(SOURCE), Paths.get("."), ".mgs").run();
        assertTrue(Files.exists(TARGET));
    }
	function tearDown(){
        Files.deleteIfExists(SOURCE);
        Files.deleteIfExists(TARGET);
    }
	function generatesNothing(){
        new Application(new SingleSourceSet(SOURCE), Paths.get("."), ".mgs").run();
        assertFalse(Files.exists(TARGET));
    }
	return {};
}
module.exports = {
	ApplicationTest
};
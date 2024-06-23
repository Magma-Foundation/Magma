import {Builder, Parser} from "xml2js";
import * as path from "node:path";
import * as fs from "node:fs/promises";

async function main() {
    const target = path.join(process.cwd(), "../../magmac/debug/java-mgs/error.xml");
    const value = await fs.readFile(target);
    const valueAsString = value.toString();

    const parser = new Parser();
    const result = await parser.parseStringPromise(valueAsString);
    console.log(result);
}

main().catch(e => {
    console.error(e);
});

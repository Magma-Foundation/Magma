import {Parser} from "xml2js";
import * as path from "node:path";
import * as fs from "node:fs/promises";
import Koa from 'koa';
import bodyParser from 'koa-bodyparser';
import cors from '@koa/cors';
import Router from "@koa/router";

async function main() {
    const target = path.join(process.cwd(), "../../magmac/debug/java-mgs/error.xml");
    const value = await fs.readFile(target);
    const valueAsString = value.toString();

    const parser = new Parser();
    const result = await parser.parseStringPromise(valueAsString);

    const app = new Koa();
    const router = new Router();

    app.use(cors());
    app.use(bodyParser());

    router.get('/', ctx => {
        ctx.body = result;
        ctx.status = 200;
    });

    app.use(router.routes());
    app.use(router.allowedMethods());

    const PORT = 3000;
    app.listen(PORT, () => {
        console.log(`Server is running on http://localhost:${PORT}`);
    });
}

main().catch(e => {
    console.error(e);
});

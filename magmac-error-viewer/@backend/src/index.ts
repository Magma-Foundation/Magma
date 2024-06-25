import {Parser} from "xml2js";
import * as path from "node:path";
import * as fs from "node:fs/promises";
import Koa from 'koa';
import bodyParser from 'koa-bodyparser';
import cors from '@koa/cors';
import Router from "@koa/router";

const DEFAULT_PATH = path.join(process.cwd(), "../../magmac/debug/java-mgs/error.xml");

async function read(path: string = DEFAULT_PATH) {
    const value = await fs.readFile(path);
    const valueAsString = value.toString();

    const parser = new Parser();
    return await parser.parseStringPromise(valueAsString);
}

async function main() {
    const app = new Koa();
    const router = new Router();

    app.use(cors());
    app.use(bodyParser());

    function findPath(body: Record<string, string>) {
        const providedPath = (body && typeof body === "object") ? body["path"] : undefined;
        return providedPath?.toString() ?? DEFAULT_PATH;
    }

    router.post('/content', async ctx => {
        const body = ctx.request.body as Record<string, string>;
        const actualPath = findPath(body);
        const result = await read(actualPath);
        ctx.body = result.parent.$.context;
        ctx.status = 200;
    });

    router.post("/tree", async ctx => {
        const body = ctx.request.body as Record<string, string>;
        const actualPath = findPath(body);
        ctx.body = await read(actualPath);
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

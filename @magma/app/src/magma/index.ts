import bodyParser from "body-parser";
import cors from "cors";
import express from "express";
import * as fs from "fs/promises";

const PORT = process.env.PORT || 3000;

const app = express();
app.use(express.json());
app.use(cors());
app.use(bodyParser.json());

import * as paths from "path";

app.get("/", async (req, res) => {
    const path = paths.join(process.cwd(), "src", "index.ts"); // Adjust as needed
    const buffer = await fs.readFile(path, { encoding: "utf-8" });

    res.setHeader("Content-Type", "text/plain"); // ✅ Correct MIME type
    res.send(buffer);
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}.`);
});

import { Path } from "../../../magma/api/io/Path";
import { FilesInstance } from "./FilesInstance";

export const Files : FilesInstance = {
    get: function (first: string, ...more: string[]): Path {
        throw new Error("Function not implemented.");
    }
};
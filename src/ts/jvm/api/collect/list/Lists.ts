import { List } from "../../../../magma/api/collect/list/List";
import { ListsInstance } from "./ListsInstance";

export const Lists: ListsInstance = {
    empty: function <T>(): List<T> {
        throw new Error("Function not implemented.");
    },
    of: function <T>(...elements: T[]): List<T> {
        throw new Error("Function not implemented.");
    },
    fromArray: function <T>(elements: T[]): List<T> {
        throw new Error("Function not implemented.");
    }
};
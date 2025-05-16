import { ConsoleInstance } from "./ConsoleInstance";

export const Console: ConsoleInstance = {
    printErrLn: function (message: string): void {
        console.error(message);
    }
};
import { CharactersInstance } from "./CharactersInstance";

export const Characters: CharactersInstance = {
    isDigit(c: string): boolean {
        if (c.length !== 1) return false;
        const code = c.charCodeAt(0);
        return code >= 48 && code <= 57;  // '0'..'9'
    },

    isLetter(c: string): boolean {
        if (c.length !== 1) return false;
        const code = c.charCodeAt(0);
        return (code >= 65 && code <= 90)    // 'A'..'Z'
            || (code >= 97 && code <= 122);  // 'a'..'z'
    }
};

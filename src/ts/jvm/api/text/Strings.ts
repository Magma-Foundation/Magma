import { StringsInstance } from "./StringsInstance";

export const Strings: StringsInstance = {
    length(str: string): number {
        return str.length;
    },

    sliceBetween(input: string, startInclusive: number, endExclusive: number): string {
        return input.slice(startInclusive, endExclusive);
    },

    sliceFrom(input: string, startInclusive: number): string {
        return input.slice(startInclusive);
    },

    isEmpty(value: string): boolean {
        return value.length === 0;
    },

    equalsTo(left: string, right: string): boolean {
        return left === right;
    },

    strip(input: string): string {
        return input.trim();
    },

    isBlank(value: string): boolean {
        return value.trim().length === 0;
    }
};
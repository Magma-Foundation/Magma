export const env = new Map<string, string>();
for (const entry of Object.entries(process.env)) {
    const key = entry[0];
    const value = entry[1];
    if (value) {
        env.set(key, value);
    }
}



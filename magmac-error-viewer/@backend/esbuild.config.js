const esbuild = require('esbuild');

esbuild.build({
    entryPoints: ['./src/index.ts'],
    bundle: true,
    outfile: './dist/bundle.js',
    platform: 'node', // or 'browser' if you're targeting the browser
    sourcemap: true, // optional, for debugging
    minify: true, // optional, for production
}).catch(() => process.exit(1));

const { join } = require('path');

/** @type {import('tailwindcss').Config} */
module.exports = {
    darkMode: 'class',
    content: [
        join(__dirname, 'src/**/!(*.stories|*.spec).{ts,html}'),
        // ...createGlobPatternsForDependencies(__dirname),
    ],
    theme: {
        extend: {
            colors: {
                "primary": "#204E4A",
                "industrial-gray": "#E2E8F0",
                "background-light": "#F8FAFB",
                "background-dark": "#0F172A",
                "surface": "#FFFFFF",
                "background": "#F8FAFB",
                "accent": "#204E4A",
            },
            fontFamily: {
                "sans": ["Inter", "sans-serif"]
            },
            boxShadow: {
                'ultra-soft': '0 4px 20px -2px rgba(0, 0, 0, 0.03), 0 2px 12px -4px rgba(0, 0, 0, 0.02)',
                'premium': '0 10px 30px -5px rgba(32, 78, 74, 0.04), 0 4px 12px -2px rgba(0, 0, 0, 0.01)'
            }
        },
    },
    plugins: [
        // require('@tailwindcss/forms'), // Note: might need installation if not present
        // require('@tailwindcss/container-queries'),
    ],
};

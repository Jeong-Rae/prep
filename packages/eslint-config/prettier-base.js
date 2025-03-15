/** @type {import("prettier").Config} */
module.exports = {
  singleQuote: true,
  semi: true,
  tabWidth: 2,
  endOfLine: 'lf',
  useTabs: false,
  arrowParens: 'always',
  printWidth: 100,
  trailingComma: 'all',
  bracketSpacing: true,
  quoteProps: 'as-needed',
  proseWrap: 'preserve',
  overrides: [
    {
      files: '*.json',
      options: {
        tabWidth: 2,
      },
    },
  ],
};

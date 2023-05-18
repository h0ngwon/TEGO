module.exports = {
  env: {
    browser: true,
    es2021: true,
  },
  extends: [
    "eslint:recommended",
    "plugin:react/recommended",
    "react-app",
    "airbnb",
    "plugin:prettier/recommended",
    "prettier",
  ],
  parserOptions: {
    ecmaFeatures: {
      jsx: true,
    },
    ecmaVersion: "latest",
    sourceType: "module",
  },
  plugins: ["prettier"],
  rules: {
    "react/react-in-jsx-scope": "off",
    "no-unused-vars": 0,
    "react/prop-types": "off",
    "react/jsx-filename-extension": [1, { extensions: ["js", "jsx"] }],
    "prettier/prettier": ["error", { endOfLine: "auto" }],
    "no-plusplus": ["error", { allowForLoopAfterthoughts: true }],
    "react/no-unstable-nested-components": ["off"],
    "import/prefer-default-export": "off",
    "react/jsx-props-no-spreading": "off",
    "import/no-unresolved": ["error", { caseSensitive: false }],
  },
};

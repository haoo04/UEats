# UEats - Admin Portal

## 1. Introduction

A comprehensive backend management system for the UEats food delivery platform.

### Features

- Store Management
- Employee Management
- Category Management (Dishes/Combos)
- Dish Management
- Combo Meal Management
- Order Management
- Customer Statistics

## 2. Technology Stack

- **Frontend:** Vue.js + TypeScript
- **UI Framework:** Element UI
- **Routing:** Vue Router
- **State Management:** Vuex
- **HTTP Client:** Axios
- **Styling:** Sass

## 3. Project Structure

```
├── public                     # Static assets (directly copied)
│   │── favicon.ico            # Favicon
│   │── manifest.json          # PWA configuration file
│   │── img                    # Static images
│   └── index.html             # HTML template
├── src                        # Source code
│   ├── api                    # All API requests
│   ├── assets                 # Static assets processed by webpack (themes, fonts, etc.)
│   ├── components             # Global components
│   ├── icons                  # SVG icons
│   ├── layout                 # Global layout
│   ├── router                 # Routing configuration
│   ├── store                  # Global Vuex store
│   ├── styles                 # Global styles
│   ├── utils                  # Utility functions
│   ├── views                  # All pages/views
│   ├── App.vue                # Root App component
│   ├── main.ts                # Main entry file (component loading, initialization)
│   ├── permission.ts          # Permission handling
│   └── shims-vue.d.ts         # Module declarations
├── dist                       # Build output directory
├── tests                      # Tests
├── .browserslistrc            # Browserslist configuration (for Autoprefixer)
├── .editorconfig              # Editor configuration
├── .env.xxx                   # Environment variable configuration
├── .eslintrc.js               # ESLint configuration
├── babel.config.js            # Babel loader configuration
├── cypress.json               # E2E test configuration
├── jest.config.js             # Jest unit test configuration
├── package.json               # Project dependencies
├── postcss.config.js          # PostCSS configuration
├── tsconfig.json              # TypeScript configuration
└── vue.config.js              # vue-cli configuration
```

### Documentation Reference

- [Vue.js](https://vuejs.org/v2/guide/)

## 4. Getting Started

##### Set the image preview address in `src/config.json` before deploying to a server.

Example:
```json
{
  "baseUrl": "http://your-server-address"
}
```

##### Install dependencies

```bash
yarn install
# or
npm install
```

##### Start the local development server (with hot-reloading)

```bash
yarn serve
# or
npm run serve
```

##### Build for production

```bash
yarn build
# or
npm run build
```

##### Build for a staging/UAT environment

```bash
yarn build:uat
# or
npm run build:uat
```

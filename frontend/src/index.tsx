import React from "react";
import ReactDOM from "react-dom/client";
import "./assets/global.css";
import { Provider } from "react-redux";
import { store } from "./redux/store";

import { App } from "./App";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

root.render(
  <Provider store={store}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </Provider>
);

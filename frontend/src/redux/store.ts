import { configureStore } from "@reduxjs/toolkit";
import registerReducer from '../redux/Slices/RegisterSlice'
import { register } from "module";

export const store = configureStore({

    reducer : {
        register: registerReducer
    }
})


export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;
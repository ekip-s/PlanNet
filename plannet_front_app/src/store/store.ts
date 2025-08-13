import { configureStore } from "@reduxjs/toolkit";
import formSlice from "./form_slice.ts";

const store = configureStore({
    reducer: {
        form: formSlice.reducer,
    },
});

export type RootState = ReturnType<typeof store.getState>;
export default store;

/*
const dispatchActions = useDispatch();

dispatchActions(
        authActions.setUser({
          token,
          clientId: decodedToken.sub,
          login: decodedToken.preferred_username || "",
          firstName: decodedToken.given_name || "",
          lastName: decodedToken.family_name || "",
          email: decodedToken.email || "",
        })

const isAuth = useSelector((state: RootState) => state.auth.isAuth);
 */
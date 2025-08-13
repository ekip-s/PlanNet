import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface FormState {
    type: "actions" | undefined;
    id: string;
    state: boolean;
    title: string;
}

const initialState: FormState = {
    type: undefined,
    id: "",
    state: false,
    title: ""
}

const formSlice = createSlice({
    name: "form",
    initialState,
    reducers: {
        setFormData: (state: FormState, action: PayloadAction<FormState>) => {
            state.type = action.payload.type;
            state.id = action.payload.id;
            state.state = action.payload.state
            state.title = action.payload.title;
        },
        onHide(state: FormState) {
            state.type = undefined;
            state.id = "";
            state.state = false;
            state.title = "";
        }
    }
})

export const formActions = formSlice.actions;
export default formSlice;
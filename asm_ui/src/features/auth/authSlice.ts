import { createSlice, type PayloadAction } from '@reduxjs/toolkit';

interface AuthState {
  token: string | null;
  roles: string[];
  isAuthenticated: boolean;
}

const initialState: AuthState = {
  token: localStorage.getItem('token'),
  roles: JSON.parse(localStorage.getItem('roles') || '[]'),
  isAuthenticated: !!localStorage.getItem('token'),
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setCredentials: (
      state,
      action: PayloadAction<{ token: string; roles: string[] }>,
    ) => {
      state.token = action.payload.token;
      state.roles = action.payload.roles;
      state.isAuthenticated = true;
      localStorage.setItem('token', action.payload.token);
      localStorage.setItem('roles', JSON.stringify(action.payload.roles));
    },
    logout: (state) => {
      state.token = null;
      state.roles = [];
      state.isAuthenticated = false;
      localStorage.removeItem('token');
      localStorage.removeItem('roles');
    },
  },
});

export const { setCredentials, logout } = authSlice.actions;
export default authSlice.reducer;

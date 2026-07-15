import { configureStore } from '@reduxjs/toolkit';
import { setupListeners } from '@reduxjs/toolkit/query';
import authReducer from '@/features/auth/authSlice';
import { authApi } from '@/features/auth/authApi';
import { employeesApi } from '@/features/employees/employeesApi';
import { projectsApi } from '@/features/projects/projectsApi';
import { allocationsApi } from '@/features/allocations/allocationsApi';
import { reportsApi } from '@/features/reports/reportsApi';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    [authApi.reducerPath]: authApi.reducer,
    [employeesApi.reducerPath]: employeesApi.reducer,
    [projectsApi.reducerPath]: projectsApi.reducer,
    [allocationsApi.reducerPath]: allocationsApi.reducer,
    [reportsApi.reducerPath]: reportsApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(
      authApi.middleware,
      employeesApi.middleware,
      projectsApi.middleware,
      allocationsApi.middleware,
      reportsApi.middleware,
    ),
});

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

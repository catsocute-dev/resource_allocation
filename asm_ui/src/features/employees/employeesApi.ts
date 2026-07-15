import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type {
  ApiResponse,
  PagingResponse,
  PagingParams,
  Employee,
  EmployeeCreateRequest,
} from '@/types';

export const employeesApi = createApi({
  reducerPath: 'employeesApi',
  baseQuery: fetchBaseQuery({
    baseUrl: '/api/v1/employees',
    prepareHeaders: (headers) => {
      const token = localStorage.getItem('token');
      if (token) {
        headers.set('Authorization', `Bearer ${token}`);
      }
      return headers;
    },
  }),
  tagTypes: ['Employee'],
  endpoints: (builder) => ({
    getEmployees: builder.query<PagingResponse<Employee>, PagingParams>({
      query: (params) => ({
        url: '',
        params: {
          page: params.page ?? 1,
          size: params.size ?? 10,
          direction: params.direction ?? 'DESC',
          field: params.field ?? 'createdAt',
        },
      }),
      transformResponse: (response: ApiResponse<PagingResponse<Employee>>) =>
        response.data,
      providesTags: ['Employee'],
    }),
    getEmployeeById: builder.query<Employee, string>({
      query: (id) => `/${id}`,
      transformResponse: (response: ApiResponse<Employee>) => response.data,
    }),
    createEmployee: builder.mutation<Employee, EmployeeCreateRequest>({
      query: (body) => ({
        url: '',
        method: 'POST',
        body,
      }),
      transformResponse: (response: ApiResponse<Employee>) => response.data,
      invalidatesTags: ['Employee'],
    }),
  }),
});

export const {
  useGetEmployeesQuery,
  useGetEmployeeByIdQuery,
  useCreateEmployeeMutation,
} = employeesApi;

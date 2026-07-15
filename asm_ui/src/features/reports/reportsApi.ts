import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type {
  ApiResponse,
  UtilizationResponse,
  WorkloadResponse,
} from '@/types';

export const reportsApi = createApi({
  reducerPath: 'reportsApi',
  baseQuery: fetchBaseQuery({
    baseUrl: '/api/v1',
    prepareHeaders: (headers) => {
      const token = localStorage.getItem('token');
      if (token) {
        headers.set('Authorization', `Bearer ${token}`);
      }
      return headers;
    },
  }),
  tagTypes: ['Report'],
  endpoints: (builder) => ({
    getUtilizationReport: builder.query<UtilizationResponse[], void>({
      query: () => '/reports/utilization',
      transformResponse: (response: ApiResponse<UtilizationResponse[]>) =>
        response.data,
      providesTags: ['Report'],
    }),
    getAvailableResources: builder.query<UtilizationResponse[], void>({
      query: () => '/reports/available',
      transformResponse: (response: ApiResponse<UtilizationResponse[]>) =>
        response.data,
      providesTags: ['Report'],
    }),
    getOverloadedEmployees: builder.query<UtilizationResponse[], void>({
      query: () => '/reports/overloaded',
      transformResponse: (response: ApiResponse<UtilizationResponse[]>) =>
        response.data,
      providesTags: ['Report'],
    }),
    getEmployeeWorkload: builder.query<WorkloadResponse, string>({
      query: (id) => `/employees/${id}/workload`,
      transformResponse: (response: ApiResponse<WorkloadResponse>) =>
        response.data,
    }),
  }),
});

export const {
  useGetUtilizationReportQuery,
  useGetAvailableResourcesQuery,
  useGetOverloadedEmployeesQuery,
  useGetEmployeeWorkloadQuery,
} = reportsApi;

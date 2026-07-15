import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type {
  ApiResponse,
  Allocation,
  AllocationCreateRequest,
  AllocationUpdateRequest,
} from '@/types';

export const allocationsApi = createApi({
  reducerPath: 'allocationsApi',
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
  tagTypes: ['Allocation'],
  endpoints: (builder) => ({
    createAllocation: builder.mutation<Allocation, AllocationCreateRequest>({
      query: (body) => ({
        url: '/allocations',
        method: 'POST',
        body,
      }),
      transformResponse: (response: ApiResponse<Allocation>) => response.data,
      invalidatesTags: ['Allocation'],
    }),
    updateAllocation: builder.mutation<
      Allocation,
      { id: string; body: AllocationUpdateRequest }
    >({
      query: ({ id, body }) => ({
        url: `/allocations/${id}`,
        method: 'PUT',
        body,
      }),
      transformResponse: (response: ApiResponse<Allocation>) => response.data,
      invalidatesTags: ['Allocation'],
    }),
    deleteAllocation: builder.mutation<void, string>({
      query: (id) => ({
        url: `/allocations/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Allocation'],
    }),
  }),
});

export const {
  useCreateAllocationMutation,
  useUpdateAllocationMutation,
  useDeleteAllocationMutation,
} = allocationsApi;

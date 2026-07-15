import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type {
  ApiResponse,
  PagingResponse,
  PagingParams,
  Project,
  ProjectCreateRequest,
} from '@/types';

export const projectsApi = createApi({
  reducerPath: 'projectsApi',
  baseQuery: fetchBaseQuery({
    baseUrl: '/api/v1/projects',
    prepareHeaders: (headers) => {
      const token = localStorage.getItem('token');
      if (token) {
        headers.set('Authorization', `Bearer ${token}`);
      }
      return headers;
    },
  }),
  tagTypes: ['Project'],
  endpoints: (builder) => ({
    getProjects: builder.query<PagingResponse<Project>, PagingParams>({
      query: (params) => ({
        url: '',
        params: {
          page: params.page ?? 1,
          size: params.size ?? 10,
          direction: params.direction ?? 'DESC',
          field: params.field ?? 'createdAt',
        },
      }),
      transformResponse: (response: ApiResponse<PagingResponse<Project>>) =>
        response.data,
      providesTags: ['Project'],
    }),
    getProjectById: builder.query<Project, string>({
      query: (id) => `/${id}`,
      transformResponse: (response: ApiResponse<Project>) => response.data,
    }),
    createProject: builder.mutation<Project, ProjectCreateRequest>({
      query: (body) => ({
        url: '',
        method: 'POST',
        body,
      }),
      transformResponse: (response: ApiResponse<Project>) => response.data,
      invalidatesTags: ['Project'],
    }),
  }),
});

export const {
  useGetProjectsQuery,
  useGetProjectByIdQuery,
  useCreateProjectMutation,
} = projectsApi;

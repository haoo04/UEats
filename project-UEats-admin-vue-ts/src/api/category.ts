import request from '@/utils/request';
/**
 *
 * Category Management
 *
 **/

// Query Category List
export const getCategoryPage = (params: any) => {
  return request({
    url: '/category/page',
    method: 'get',
    params
  });
};

// Delete Current Column
export const deleCategory = (ids: string) => {
  return request({
    url: '/category',
    method: 'delete',
    params: { id:ids }
  });
};

// Modify
export const editCategory = (params: any) => {
  return request({
    url: '/category',
    method: 'put',
    data: { ...params }
  });
};

// Add
export const addCategory = (params: any) => {
  return request({
    url: '/category',
    method: 'post',
    data: { ...params }
  });
};

// Modify---Enable/Disable
export const enableOrDisableEmployee = (params: any) => {
  return request({
    url: `/category/status/${params.status}`,
    method: 'post',
    params: { id:params.id }
  })
}

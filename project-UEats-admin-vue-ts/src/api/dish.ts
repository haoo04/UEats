import request from '@/utils/request'
/**
 *
 * Dish Management
 *
 **/
// Query List Interface
export const getDishPage = (params: any) => {
  return request({
    url: '/dish/page',
    method: 'get',
    params
  })
}

// Delete Interface
export const deleteDish = (ids: string) => {
  return request({
    url: '/dish',
    method: 'delete',
    params: { ids }
  })
}

// Modify Interface
export const editDish = (params: any) => {
  return request({
    url: '/dish',
    method: 'put',
    data: { ...params }
  })
}

// Add Interface
export const addDish = (params: any) => {
  return request({
    url: '/dish',
    method: 'post',
    data: { ...params }
  })
}

// Query Details
export const queryDishById = (id: string | (string | null)[]) => {
  return request({
    url: `/dish/${id}`,
    method: 'get'
  })
}

// Get Dish Category List
export const getCategoryList = (params: any) => {
  return request({
    url: '/category/list',
    method: 'get',
    params
  })
}

// Query Dish List Interface
export const queryDishList = (params: any) => {
  return request({
    url: '/dish/list',
    method: 'get',
    params
  })
}

// File Down Preview
export const commonDownload = (params: any) => {
  return request({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    url: '/common/download',
    method: 'get',
    params
  })
}

// Start/Stop Sale---Batch Start/Stop Sale Interface
export const dishStatusByStatus = (params: any) => {
  return request({
    url: `/dish/status/${params.status}`,
    method: 'post',
    params: { id: params.id }
  })
}

// Dish Category Data Query
export const dishCategoryList = (params: any) => {
  return request({
    url: `/category/list`,
    method: 'get',
    params: { ...params }
  })
}

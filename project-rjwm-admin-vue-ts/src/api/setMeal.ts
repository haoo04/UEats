import request from '@/utils/request'
/**
 *
 * Set Meal Management
 *
 **/

// Query List Data
export const getSetmealPage = (params: any) => {
  return request({
    url: '/setmeal/page',
    method: 'get',
    params,
  },)
}

// Delete Data Interface
export const deleteSetmeal = (ids: string) => {
  return request({
    url: '/setmeal',
    method: 'delete',
    params: { ids }
  })
}

// Modify Data Interface
export const editSetmeal = (params: any) => {
  return request({
    url: '/setmeal',
    method: 'put',
    data: { ...params }
  })
}

// Add Data Interface
export const addSetmeal = (params: any) => {
  return request({
    url: '/setmeal',
    method: 'post',
    data: { ...params }
  })
}

// Query Details Interface
export const querySetmealById = (id: string | (string | null)[]) => {
  return request({
    url: `/setmeal/${id}`,
    method: 'get'
  })
}

// Batch Start/Stop Sale
export const setmealStatusByStatus = (params: any) => {
  return request({
    url: `/setmeal/status/${params.status}`,
    method: 'post',
    params: { id: params.ids }
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

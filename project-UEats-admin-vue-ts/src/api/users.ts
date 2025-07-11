import request from '@/utils/request'
// Modify Password
export const editPassword = (data: any) =>
  request({
    'url': '/employee/editPassword',
    'method': 'put',
    data
  })
  // Get Business Status
  export const getStatus = () =>
  request({
    'url': `/shop/status`,
    'method': 'get'
  })
    // Set Business Status
    export const setStatus = (data:any) =>
    request({
      'url': `/shop/`+data,
      'method': 'put',
      'data':data
    })
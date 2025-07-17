import request from '@/utils/request'
  // Get List Data
  export const getInformData = (params: any) => {
    return request({
      url: '/messages/page',
      method: 'get',
      params,
    },)
  }
  // Get Unread
  export const getCountUnread = () => {
    return request({
      url: '/messages/countUnread',
      method: 'get'
    },)
  }
  // All Read
  export const batchMsg = (data: any) => {
    return request({
      url: '/messages/batch',
      method: 'put',
      data
    })
  }
    // Mark as Read
    export const setStatus = (params: any) => {
      return request({
        url: `/messages/${params}`,
        method: 'PUT'
      })
    }
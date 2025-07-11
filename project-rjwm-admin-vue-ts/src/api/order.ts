import request from '@/utils/request'

// Query List Page Interface
export const getOrderDetailPage = (params: any) => {
  return request({
    url: '/order/conditionSearch',
    method: 'get',
    params
  })
}

// View Interface
export const queryOrderDetailById = (params: any) => {
  return request({
    url: `/order/details/${params.orderId}`,
    method: 'get'
  })
}

// Delivery Interface
export const deliveryOrder = (params: any) => {
  return request({
    url: `/order/delivery/${params.id}`,
    method: 'put' /*  */
  })
}
// Complete Interface
export const completeOrder = (params: any) => {
  return request({
    url: `/order/complete/${params.id}`,
    method: 'put' /*  */
  })
}

// Order Cancel
export const orderCancel = (params: any) => {
  return request({
    url: '/order/cancel',
    method: 'put' /*  */,
    data: { ...params }
  })
}

// Accept Order
export const orderAccept = (params: any) => {
  return request({
    url: '/order/confirm',
    method: 'put' /*  */,
    data: { ...params }
  })
}

// Reject Order
export const orderReject = (params: any) => {
  return request({
    url: '/order/rejection',
    method: 'put' /*  */,
    data: { ...params }
  })
}

// Get the number of pending processing, pending delivery, and in-transit
export const getOrderListBy = (params: any) => {
  return request({
    url: '/order/statistics',
    method: 'get' /*  */
  })
}

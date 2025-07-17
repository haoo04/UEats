import request from '@/utils/request'
// Revenue Data
// export const getTurnoverDataes = (data) =>
//   request({
//     'url': `/report/turnoverStatistics`,
//     'method': 'get',
//     data
//   })
// Home Page Data
// // Today Data
// export const getTodayDataes = () =>
//   request({
//     'url': `/workspace/todaydate`,
//     'method': 'get'
//   })
// Order Management
  export const getOrderData = () =>
  request({
    'url': `/workspace/overviewOrders`,
    'method': 'get'
  })
// Dish Overview
export const getOverviewDishes = () =>
request({
  'url': `/workspace/overviewDishes`,
  'method': 'get'
})
// Set Meal Overview
export const getSetMealStatistics = () =>
request({
  'url': `/workspace/overviewSetmeals`,
  'method': 'get'
})
// Business Data
export const getBusinessData= () =>
request({
  'url': `/workspace/businessData`,
  'method': 'get'
})
/**
 *
 * Report Data
 *
 **/
// Statistics
// Get the current day's sales data -> Top Data
// export const getDataes = (params: any) =>
//   request({
//     'url': `/report/amountCollect/${params.date}`,
//     'method': 'get'
//   })


// Revenue Statistics
export const getTurnoverStatistics= (params: any) =>
  request({
    'url': `/report/turnoverStatistics`,
    'method': 'get',
    params
  })

// User Statistics
export const getUserStatistics= (params: any) =>
  request({
    'url': `/report/userStatistics`,
    'method': 'get',
    params
  })
  // Order Statistics
export const getOrderStatistics= (params: any) =>
request({
  'url': `/report/ordersStatistics`,
  'method': 'get',
  params
})
  // Sales Ranking TOP10
  export const getTop= (params: any) =>
  request({
    'url': `/report/top10`,
    'method': 'get',
    params
  })
  // Data Overview
  export const getDataOverView= (params: any) =>
  request({
    'url': `/report/dataOverView`,
    'method': 'get',
    params
  })
  // Export
  export function exportInfor() {
    return request({
      url: '/report/export',
      method: 'get',
      responseType: "blob"
    })
  }

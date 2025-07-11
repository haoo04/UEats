import request from '@/utils/request'
/**
 *
 * Report Data
 *
 **/

// Get the current day's sales data -> Top Data
export const getDataes = (params: any) =>
  request({
    'url': `/report/amountCollect/${params.date}`,
    'method': 'get'
  })

// Get the current day's sales data -> Top Data - Revenue Overview
export const getChartsDataes = (params: any) =>
  request({
    'url': `/report/dayCollect/${params.start}/${params.end}`,
    'method': 'get'
  })

// Get the current day's sales trend data (24 hours) -> Sales Trend
export const getDayDataes= (params: any) =>
  request({
    'url': `/report/hourCollect/${params.type}/${params.date}`,
    'method': 'get'
  })

// Payment Type Data Summary -> In-Store Collection - Current Day
export const getDayPayType = (params: any) =>
  request({
    'url': `/report/payTypeCollect/${params.date}`,
    'method': 'get'
  })
// Get the current day's various types of discount data summary -> Discount Indicators
export const getprivilege = (params: any) =>
  request({
    'url': `/report/privilegeCollect/${params.date}`,
    'method': 'get'
  })

// Get the sales ranking of dish categories -> Dish Category Proportion - Current Day
export const getSalesRanking = (params: any) =>
  request({
    'url': `/report/categoryCollect/${params.type}/${params.date}`,
    'method': 'get'
  })

// Get the current day's dish sales ranking
export const getDayRanking = (params: any) =>
  request({
    'url': `/report/currentDishRank/${params.date}`,
    'method': 'get'
  })

// Get the sales trend within a certain period of time -> Sales Trend Chart
export const getTimeQuantumDataes = (params: any) =>
  request({
    'url': `/report/dayAmountCollect/${params.type}/${params.start}/${params.end}`,
    'method': 'get'
  })

// Get the summary of various payment types within a certain period of time -> In-Store Collection - Time Period
export const getTimeQuantumReceivables = (params: any) =>
  request({
    'url': `/report/datePayTypeCollect/${params.start}/${params.end}`,
    'method': 'get'
  })

// Get the summary of dish category sales within a certain period of time -> Dish Category Proportion - Time Period
export const getTimeQuantumType = (params: any) =>
  request({
    'url': `/report/dateCategoryCollect/${params.type}/${params.start}/${params.end}`,
    'method': 'get'
  })

// Get the sales ranking of dishes within a certain period of time -> Dish Sales Ranking
export const getTimeQuantumDishes = (params: any) =>
  request({
    'url': `/report/dishRankForDate/${params.start}/${params.end}`,
    'method': 'get'
  })

// Get the summary of discount indicators within a certain period of time -> Top Information
export const getTimeQuantumDiscount = (params: any) =>
  request({
    'url': `/report/privilegeByDate/${params.start}/${params.end}`,
    'method': 'get'
  })

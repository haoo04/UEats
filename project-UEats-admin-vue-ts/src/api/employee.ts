import request from '@/utils/request'
/**
 *
 * Employee Management
 *
 **/
// Login
export const login = (data: any) =>
  request({
    'url': '/employee/login',
    'method': 'post',
    data
  })
// Logout
 export const userLogout = (params: any) =>
 request({
   'url': `/employee/logout`,
   'method': 'post',
   params
 })

export const getEmployeeList = (params: any) => {
  return request({
    url: '/employee/page',
    method: 'get',
    params
  })
}

// Modify---Enable/Disable
export const enableOrDisableEmployee = (params: any) => {
  return request({
    url: `/employee/status/${params.status}`,
    method: 'post',
    params: { id:params.id }
  })
}

// Add---Add Employee
export const addEmployee = (params: any) => {
  return request({
    url: '/employee',
    method: 'post',
    data: { ...params }
  })
}

// Modify---Add Employee
export const editEmployee = (params: any) => {
  return request({
    url: '/employee',
    method: 'put',
    data: { ...params }
  })
}

// Modify Page Reverse Check Details Interface
export const queryEmployeeById = (id: string | (string | null)[]) => {
  return request({
    url: `/employee/${id}`,
    method: 'get'
  })
}

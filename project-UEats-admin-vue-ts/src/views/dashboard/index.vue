<template>
  <div class="dashboard-container home">
    <!-- Business Data -->
    <Overview :overviewData="overviewData" />
    <!-- end -->
    <!-- Order Management -->
    <Orderview :orderviewData="orderviewData" />
    <!-- end -->
    <div class="homeMain">
      <!-- Dish Overview -->
      <CuisineStatistics :dishesData="dishesData" />
      <!-- end -->
      <!-- Set Meal Overview -->
      <SetMealStatistics :setMealData="setMealData" />
      <!-- end -->
    </div>
    <!-- Order Information -->
    <OrderList
      :order-statics="orderStatics"
      @getOrderListBy3Status="getOrderListBy3Status"
    />
    <!-- end -->
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import {
  getBusinessData,
  getDataOverView, // Business data
  getOrderData, // Today's order data
  getOverviewDishes, // Dish overview
  getSetMealStatistics, // Set meal overview
} from '@/api/index'
import { getOrderListBy } from '@/api/order'
// Components
// Business Data
import Overview from './components/overview.vue'
// Order Management
import Orderview from './components/orderview.vue'
// Dish Overview
import CuisineStatistics from './components/cuisineStatistics.vue'
// Set Meal Overview
import SetMealStatistics from './components/setMealStatistics.vue'
// Order List
import OrderList from './components/orderList.vue'
@Component({
  name: 'Dashboard',
  components: {
    Overview,
    Orderview,
    CuisineStatistics,
    SetMealStatistics,
    OrderList,
  },
})
export default class extends Vue {
  private todayData = {} as any
  private overviewData = {}
  private orderviewData = {} as any
  private flag = 2
  private tateData = []
  private dishesData = {} as any
  private setMealData = {}
  private orderListData = []
  private counts = 0
  private page: number = 1
  private pageSize: number = 10
  private status = 2
  private orderStatics = {} as any
  created() {
    this.init()
  }
  init() {
    this.$nextTick(() => {
      this.getBusinessData()
      this.getOrderStatisticsData()
      this.getOverStatisticsData()
      this.getSetMealStatisticsData()
    })
  }
  // Get business data
  async getBusinessData() {
    const data = await getBusinessData()
    this.overviewData = data.data.data
  }
  // Get today's orders
  async getOrderStatisticsData() {
    const data = await getOrderData()
    this.orderviewData = data.data.data
  }
  // Get dish overview data
  async getOverStatisticsData() {
    const data = await getOverviewDishes()
    this.dishesData = data.data.data
  }
  // Get set meal overview data
  async getSetMealStatisticsData() {
    const data = await getSetMealStatistics()
    this.setMealData = data.data.data
  }
  // Get the number of pending, to be dispatched, and dispatching orders
  getOrderListBy3Status() {
    getOrderListBy({})
      .then((res) => {
        if (res.data.code === 1) {
          this.orderStatics = res.data.data
        } else {
          this.$message.error(res.data.msg)
        }
      })
      .catch((err) => {
        this.$message.error('Request failed: ' + err.message)
      })
  }
}
</script>

<style lang="scss">
</style>

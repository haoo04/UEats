<template>
  <div class="dashboard-container home">
    <!-- Title -->
    <TitleIndex @sendTitleInd="getTitleNum" :flag="flag" :tateData="tateData" />
    <!-- end -->
    <div class="homeMain">
      <!-- Turnover statistics -->
      <TurnoverStatistics :turnoverdata="turnoverData" />
      <!-- end -->
      <!-- User statistics -->
      <UserStatistics :userdata="userData" />
      <!-- end -->
    </div>
    <div class="homeMain homecon">
      <!-- Order statistics -->
      <OrderStatistics :orderdata="orderData" :overviewData="overviewData" />
      <!-- end -->
      <!-- Top 10 sales -->
      <Top :top10data="top10Data" />
      <!-- end -->
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import {
  get1stAndToday,
  past7Day,
  past30Day,
  pastWeek,
  pastMonth,
} from '@/utils/formValidate'
import {
  getDataOverView, //Data overview
  getTurnoverStatistics,
  getUserStatistics,
  getOrderStatistics,
  getTop,
} from '@/api/index'
// Components
// Title
import TitleIndex from './components/titleIndex.vue'
// Turnover statistics
import TurnoverStatistics from './components/turnoverStatistics.vue'
// User statistics
import UserStatistics from './components/userStatistics.vue'
// Order statistics
import OrderStatistics from './components/orderStatistics.vue'
// Ranking
import Top from './components/top10.vue'
@Component({
  name: 'Dashboard',
  components: {
    TitleIndex,
    TurnoverStatistics,
    UserStatistics,
    OrderStatistics,
    Top,
  },
})
export default class extends Vue {
  private overviewData = {} as any
  private flag = 2
  private tateData = []
  private turnoverData = {} as any
  private userData = {}
  private orderData = {
    data: {},
  } as any
  private top10Data = {}
  created() {
    //this.init(this.flag)
    this.getTitleNum(2);
  }
  // Get basic data
  init(begin: any,end:any) {
    this.$nextTick(() => {
      this.getTurnoverStatisticsData(begin,end)
      this.getUserStatisticsData(begin,end)
      this.getOrderStatisticsData(begin,end)
      this.getTopData(begin,end)
    })
  }

  // Get turnover statistics data
  async getTurnoverStatisticsData(begin: any ,end:any) {
    const data = await getTurnoverStatistics({ begin: begin,end:end })
    const turnoverData = data.data.data
    this.turnoverData = {
      dateList: turnoverData.dateList.split(','),
      turnoverList: turnoverData.turnoverList.split(',')
    }
    // this.tateData = this.turnoverData.date
    // const arr = []
    // this.tateData.forEach((val) => {
    //   let date = new Date()
    //   let year = date.getFullYear()
    //   arr.push(year + '-' + val)
    // })
    // this.tateData = arr
  }
  // Get user statistics data
  async getUserStatisticsData(begin: any ,end:any) {
    const data = await getUserStatistics({ begin: begin,end:end })
    const userData = data.data.data
    this.userData = {
      dateList: userData.dateList.split(','),
      totalUserList: userData.totalUserList.split(','),
      newUserList: userData.newUserList.split(','),
    }
  }
  // Get order statistics data
  async getOrderStatisticsData(begin: any ,end:any) {
    const data = await getOrderStatistics({begin: begin,end:end })
    const orderData = data.data.data
    this.orderData = {
      data: {
        dateList: orderData.dateList.split(','),
        orderCountList: orderData.orderCountList.split(','),
        validOrderCountList: orderData.validOrderCountList.split(','),
        //orderCompletionRateList: orderData.orderCompletionRateList.split(','),
      },
      totalOrderCount: orderData.totalOrderCount,
      validOrderCount: orderData.validOrderCount,
      orderCompletionRate: orderData.orderCompletionRate
    }
  }
  // Get ranking data
  async getTopData(begin: any ,end:any) {
    const data = await getTop({begin: begin,end:end })
    const top10Data = data.data.data
    this.top10Data = {
      nameList: top10Data.nameList.split(',').reverse(),
      numberList: top10Data.numberList.split(',').reverse(),
    }
    console.log(this.top10Data)
  }
  // Get the current selected tab time
  getTitleNum(data) {
    switch (data) {
      case 1:
        this.tateData = get1stAndToday()
        break
      case 2:
        this.tateData = past7Day()
        break
      case 3:
        this.tateData = past30Day()
        break
      case 4:
        this.tateData = pastWeek()
        break
      case 5:
        this.tateData = pastMonth()
        break
    }
    this.init(this.tateData[0],this.tateData[1])
  }
}
</script>

<style lang="scss">
</style>

<template>
  <div class="dashboard-container">
    <HeadLable :but-list="true">
      <div class="headBut">
        <span :class="{ act: act === 'day' }" @click="dateAct('day')"
          >Daily Report</span
        >
        <span :class="{ act: act === 'week' }" @click="dateAct('week')"
          >Weekly Report</span
        >
        <span :class="{ act: act === 'mouth' }" @click="dateAct('mouth')"
          >Monthly Report</span
        >
      </div>
    </HeadLable>
    <div class="topLable">
      <div class="tit">
        <span v-if="act === 'day'">Statistics Time：00：00 - 24：00</span>
        <span v-if="act === 'week'"
          >Statistics Time：{{ stateTime }} -{{ endTime }}</span
        >
        <span v-if="act === 'mouth'"
          >Statistics Time：{{ stateTime }} -{{ endTime }}</span
        >
      </div>
      <div v-if="act === 'day'" class="dataSelect">
        <div>
          <span @click="checkaffterDate('day', 'before')">Yesterday</span>
          <el-date-picker
            v-model="dataTime"
            size="mini"
            type="date"
            placeholder="Select Date"
            value-format="yyyy-MM-dd"
            :clearable="false"
            @change="changeDate('day')"
          />
          <span @click="checkaffterDate('day', 'after')">Tomorrow</span>
        </div>
        <div><span class="but" @click="init()">View Today's Data</span></div>
      </div>
      <div v-if="act === 'week'" class="dataSelect">
        <div>
          <span @click="checkaffterDate('week', 'before')">Last Week</span>
          <el-date-picker
            v-model="dataTime"
            size="mini"
            type="week"
            format="yyyy WW Week"
            value-format="yyyy-MM-dd"
            placeholder="Select Week"
            :clearable="false"
            @change="changeDate('week')"
          />
          <span @click="checkaffterDate('week', 'after')">Next Week</span>
        </div>
        <div>
          <span class="but" @click="dateAct('week')">View This Week's Data</span>
        </div>
      </div>
      <div v-if="act === 'mouth'" class="dataSelect">
        <div>
          <span @click="checkaffterDate('mouth', 'before')">Last Month</span>
          <el-date-picker
            v-model="dataTime"
            size="mini"
            type="month"
            value-format="yyyy-MM-dd"
            placeholder="Select Month"
            :clearable="false"
            @change="changeDate('mouth')"
          />
          <span @click="checkaffterDate('mouth', 'after')">Next Month</span>
        </div>
        <div>
          <span class="but" @click="dateAct('mouth')">View This Month's Data</span>
        </div>
      </div>
    </div>
    <div :key="restKey" class="container">
      <div v-if="act === 'day'" class="topDataBox">
        <div class="box nowData">
          <div class="icon">
            <img
              src="./../../assets/icons/xiangmujine@2x.png"
              width="45"
              height="43"
              alt=""
            />
          </div>
          <div class="item">
            <div>Actual Receipt Amount</div>
            <div>{{ topData.payTotal / 100 || 0 }}元</div>
            <div>Compared to the previous day 0%</div>
          </div>
        </div>
        <div class="box noData">
          <div class="icon">
            <img src="./../../assets/icons/jine_m-2@2x.png" width="50" alt="" />
          </div>
          <div class="item">
            <div>Unpaid Amount</div>
            <div>{{ topData.noPayTotal / 100 || 0 }}元</div>
            <div>Compared to the previous day 0%</div>
          </div>
        </div>
        <div class="box employee">
          <div class="icon">
            <img src="./../../assets/icons/renshu@2x.png" width="46" alt="" />
          </div>
          <div class="item">
            <div>Dining People</div>
            <div>{{ topData.totalPerson || 0 }}人</div>
            <div>Compared to the previous day 0%</div>
          </div>
        </div>
      </div>
      <div class="employeeChart" style="position: relative">
        <div class="topButBox">
          <span
            :class="{ butAct: typeA == 1 }"
            @click="topActiveHandle('typeA')"
            >By Amount</span
          >
          <span
            :class="{ butAct: typeA == 2 }"
            @click="topActiveHandle('typeA')"
            >By Order</span
          >
        </div>
        <Basic
          v-if="chartDataA"
          id="line"
          :chart-data="chartDataA"
          title="Time Period Sales Trend"
        />
      </div>
    </div>
    <div class="container">
      <div class="chartBox">
        <div style="position: relative">
          <div class="topButBox">
            <span
              :class="{ butAct: typeB == 1 }"
              @click="topActiveHandle('typeB')"
              >By Amount</span
            >
            <span
              :class="{ butAct: typeB == 2 }"
              @click="topActiveHandle('typeB')"
              >By Quantity</span
            >
          </div>
          <BarChart :chart-data="chartDataC" title="Dish Category Proportion" />
        </div>
        <div>
          <MixedChart :chart-data="chartDataB" title="Menu Sales Ranking" />
        </div>
      </div>
    </div>
    <div class="container">
      <div class="chartBox">
        <div>
          <BarChart id="bar" :chart-data="chartDataD" title="In-Store Receipt Composition" />
        </div>
        <div class="itemList">
          <div class="title">Discount Indicators</div>
          <div class="item topLab">
            <span>Discount Total</span><span>{{ discountTotal / 100 }}元</span
            ><span>{{ discountPercentTotal * 100 }}%</span>
          </div>
          <div v-for="(item, index) in discount" :key="index" class="item">
            <span>{{ item.name }}</span
            ><span>{{ item.value / 100 }}元</span
            ><span>{{ item.percent * 100 }}%</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import Basic from '@/components/Charts/Basic.vue'
import BarChart from '@/components/Charts/BarChart.vue'
import MixedChart from '@/components/Charts/MixedChart.vue'
import moment from 'moment'
import {
  getDataes,
  getDayPayType,
  getprivilege,
  getDayDataes,
  getSalesRanking,
  getDayRanking,
  getTimeQuantumDataes,
  getTimeQuantumReceivables,
  getTimeQuantumType,
  getTimeQuantumDishes,
  getTimeQuantumDiscount,
  getChartsDataes,
} from '@/api/charts'
import { isBoolean } from 'style-resources-loader/lib/utils'
import request from '@/utils/request'

@Component({
  name: 'chart',
  components: {
    HeadLable,
    Basic,
    BarChart,
    MixedChart,
  },
})
export default class extends Vue {
  private dataTime: any = ''
  private restKey = 0
  private moment = moment
  private Today = moment().format('YYYY-MM-DD')
  private week = [
    moment().startOf('week').add(1, 'day').format('YYYY-MM-DD'),
    moment().endOf('week').add(1, 'day').format('YYYY-MM-DD'),
  ]
  private month = [
    moment().startOf('month').format('YYYY-MM-DD'),
    moment().endOf('month').format('YYYY-MM-DD'),
  ]
  private topData = {}
  private stateTime = moment().format('YYYY-MM-DD')
  private endTime = moment().format('YYYY-MM-DD')
  private act = 'day'
  private dataType = 1 //Type (1: Amount; 2: Quantity)
  private typeA = 1
  private typeB = 1
  private chartDataA = {} // Sales Trend
  private chartDataB = {} // Sales Ranking

  private chartDataC = {} // Category Proportion
  private chartDataD = {} // Receipt Composition
  private discount = [] // Discount Indicators
  private discountTotal = 0
  private discountPercentTotal = 0

  created() {
    this.init()
  }

  private async init() {
    this.dataTime = this.Today
    this.stateTime = this.Today
    this.endTime = this.Today
    // await this.getData()
    await this.getTimeWuantumData()
  }

  private checkaffterDate(val: any, st: any) {
    const date = moment(this.dataTime).valueOf()
    if (st == 'before') {
      // Yesterday, Week, Month
      if (val === 'day') {
        this.dataTime = moment(date - 86400000).format('YYYY-MM-DD')
      } else if (val === 'week') {
        this.dataTime = moment(date - 86400000 * 7).format('YYYY-MM-DD')
      } else if (val === 'mouth') {
        const mouthDate = moment(this.dataTime)
          .startOf('month')
          .format('YYYY-MM-DD')
        this.dataTime = moment(moment(mouthDate).valueOf() - 86400000)
          .startOf('month')
          .format('YYYY-MM-DD')
      }
      this.changeDate(val)
    } else if (st == 'after') {
      // Tomorrow, Week, Month
      if (moment(date + 86400000).isBefore(moment())) {
        if (val === 'day') {
          this.dataTime = moment(date + 86400000).format('YYYY-MM-DD')
        } else if (val === 'week') {
          this.dataTime = moment(date + 86400000 * 7).format('YYYY-MM-DD')
        } else if (val === 'mouth') {
          const mouthDate = moment(this.dataTime)
            .endOf('month')
            .format('YYYY-MM-DD')
          this.dataTime = moment(moment(mouthDate).valueOf() + 86400000)
            .startOf('month')
            .format('YYYY-MM-DD')
        }
        this.changeDate(val)
      } else {
        let err = ''
        switch (val) {
          case 'day':
            err = 'It is already the last day'
            break
          case 'week':
            err = 'It is already the last week'
            break
          default:
            err = 'It is already the last month'
        }
        this.$message.error(err)
      }
    }
  }

  // Date selection
  private changeDate(val: string) {
    if (this.stateTime == '' || this.endTime == '' || this.dataTime == null) {
      this.$message.error('Search date cannot be empty!')
      this.dataTime = moment().format('YYYY-MM-DD')
      this.stateTime = moment().format('YYYY-MM-DD')
      this.endTime = moment().format('YYYY-MM-DD')
      return
    }
    if (val === 'day') {
      this.stateTime = moment(this.dataTime).format('YYYY-MM-DD')
      this.endTime = moment(this.dataTime).format('YYYY-MM-DD')
      if (
        moment(this.dataTime).format('YYYY-MM-DD') ===
        moment().format('YYYY-MM-DD')
      ) {
        this.getData()
      } else {
        this.getDaySalesVolumeData()
        // this.getData()
        this.getTimeWuantumData()
      }
    } else {
      if (val === 'week') {
        this.stateTime = moment(this.dataTime)
          .startOf('week')
          .add(1, 'day')
          .format('YYYY-MM-DD')
        this.endTime = moment(this.dataTime)
          .endOf('week')
          .add(1, 'day')
          .format('YYYY-MM-DD')
      } else {
        this.stateTime = moment(this.dataTime)
          .startOf('month')
          .format('YYYY-MM-DD')
        this.endTime = moment(this.dataTime).endOf('month').format('YYYY-MM-DD')
      }
      this.getTimeWuantumData()
    }
  }

  // Day, Week, Month switch
  private dateAct(val: string) {
    this.restKey++
    this.act = val
    if (val === 'day') {
      this.init()
    } else {
      if (val === 'week') {
        this.dataTime = this.Today
        this.stateTime = this.week[0]
        this.endTime = this.week[1]
      } else {
        this.dataTime = this.Today
        this.stateTime = this.month[0]
        this.endTime = this.month[1]
      }
      this.getTimeWuantumData()
    }
  }

  private topActiveHandle(act: string) {
    if (act === 'typeA') {
      this.typeA = this.typeA === 1 ? 2 : 1
      if (this.act == 'day') {
        // moment(this.dataTime).format('YYYY-MM-DD') ===  moment().format('YYYY-MM-DD')){
        // this.getDayData()
        this.getTimeQuantumData()
      } else {
        this.getTimeQuantumData()
      }
    } else {
      this.typeB = this.typeB === 1 ? 2 : 1
      if (this.act == 'day') {
        // (moment(this.dataTime).format('YYYY-MM-DD') ===  moment().format('YYYY-MM-DD')){
        // this.getSalesRankData()
        this.getTimeQuantumTypeData()
      } else {
        this.getTimeQuantumTypeData()
      }
    }
  }

  // Get today's data
  private getData() {
    this.getDayData()
    this.getSalesRankData()
    this.getDayPayTypeData()
    this.getDayRankingData()
    this.getDaySalesVolumeData()
    this.getprivilegeData()
  }

  // Get today's sales trend information - Sales Trend Chart
  private getDayData() {
    getDayDataes({ type: this.typeA, date: this.dataTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          let yData: number[] = []
          if (this.typeA === 1) {
            data.series.length > 0 &&
              data.series.map((n: number) => {
                yData.push(n / 100)
              })
          } else {
            yData = data.series
          }
          const charts = { xData: data.xaxis, yData: yData }
          this.chartDataA = charts
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }
  // Get dish category sales ranking - Dish Category Proportion - Today
  private getSalesRankData() {
    getSalesRanking({ type: this.typeB, date: this.dataTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          let chartData = []
          if (this.typeB === 1) {
            data.length > 0 &&
              data.map((n: { name: string; percent: any; value: number }) => {
                chartData.push({ ...n, value: n.value / 100 })
              })
          } else {
            chartData = data
          }
          let charts = {
            legendData: [],
            seriesData: chartData,
            selected: {},
          }
          data &&
            data.length > 0 &&
            data.forEach((item: any) => {
              ;(charts.legendData as Array<string>).push(item.name as string)
              ;(charts.selected as any)[item.name] = true
            })
          this.chartDataC = charts
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }
  // Payment type data summary - In-store receipt composition - Today
  private getDayPayTypeData() {
    getDayPayType({ date: this.dataTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          let chartData = []
          if (this.typeB === 1) {
            data.length > 0 &&
              data.map((n: { name: string; percent: any; value: number }) => {
                chartData.push({ ...n, value: n.value / 100 })
              })
          } else {
            chartData = data
          }
          let charts = {
            legendData: [],
            seriesData: chartData,
            selected: {},
          }
          data &&
            data.length > 0 &&
            data.forEach((item: any) => {
              ;(charts.legendData as Array<string>).push(item.name as string)
              ;(charts.selected as any)[item.name] = true
            })
          this.chartDataD = charts
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }
  // Get today's dish sales ranking - Sales ranking chart
  private getDayRankingData() {
    getDayRanking({ type: this.dataType, date: this.dataTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          const charts = { xData: data.xaxis, yData: data.series }
          this.chartDataB = charts
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }
  // Get the number of sales in one day - Top data
  private getDaySalesVolumeData() {
    // Get today's sales data
    getChartsDataes({ start: this.dataTime, end: this.dataTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          this.topData = data
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }
  // Get the summary of various types of discounts in one day
  private getprivilegeData() {
    getprivilege({ date: this.dataTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          this.discountTotal = 0
          this.discountPercentTotal = 0
          data &&
            data.dataList.length > 0 &&
            data.dataList.forEach((item: any) => {
              this.discountTotal += item.value
              this.discountPercentTotal += item.percent
            })
          this.discount = data.dataList
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }

  // View time period interface call
  private getTimeWuantumData() {
    this.getTimeQuantumData()
    this.getReceivables()
    this.getTimeQuantumTypeData()
    this.getTimeQuantumDishesDataes()
    this.getDaySalesVolumeData()
    this.getDiscount()
  }

  // Time period data acquisition
  // Get the summary of various types of discounts in one day
  private getDiscount() {
    getTimeQuantumDiscount({ start: this.stateTime, end: this.endTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          this.discountTotal = 0
          this.discountPercentTotal = 0
          data &&
            data.dataList.length > 0 &&
            data.dataList.forEach((item: any) => {
              this.discountTotal += item.value
              this.discountPercentTotal += item.percent
            })
          this.discount = data.dataList
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }
  // Get the sales trend within a certain date
  private getTimeQuantumData() {
    getTimeQuantumDataes({
      type: this.typeA,
      start: this.stateTime,
      end: this.endTime,
    }).then((res) => {
      if (res.data.code == 200) {
        const { data } = res.data
        let yData: number[] = []
        if (this.typeA === 1) {
          data.series.length > 0 &&
            data.series.map((n: number) => {
              yData.push(n / 100)
            })
        } else {
          yData = data.series
        }
        const charts = { xData: data.xaxis, yData: yData }
        this.chartDataA = charts
      } else {
        this.$message.error(res.data.desc)
      }
    })
  }
  // Get the summary of various types of payment types in one day - In-store receipt composition - Time period
  private getReceivables() {
    getTimeQuantumReceivables({ start: this.stateTime, end: this.endTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          let chartData = []
          if (this.typeB === 1) {
            data.length > 0 &&
              data.map((n: { name: string; percent: any; value: number }) => {
                chartData.push({ ...n, value: n.value / 100 })
              })
          } else {
            chartData = data
          }
          let charts = {
            legendData: [],
            seriesData: chartData,
            selected: {},
          }
          data &&
            data.length > 0 &&
            data.forEach((item: any) => {
              ;(charts.legendData as Array<string>).push(item.name as string)
              ;(charts.selected as any)[item.name] = true
            })
          this.chartDataD = charts
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }

  // Get the summary of various types of dish sales in one day - Dish Category Proportion - Time period
  private getTimeQuantumTypeData() {
    getTimeQuantumType({
      type: this.typeB,
      start: this.stateTime,
      end: this.endTime,
    })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          let chartData = []
          if (this.typeB === 1) {
            data.length > 0 &&
              data.map((n: { name: string; percent: any; value: number }) => {
                chartData.push({ ...n, value: n.value / 100 })
              })
          } else {
            chartData = data
          }
          let charts = {
            legendData: [],
            seriesData: chartData,
            selected: {},
          }
          data.length > 0 &&
            data.forEach((item: any) => {
              ;(charts.legendData as Array<string>).push(item.name as string)
              ;(charts.selected as any)[item.name] = true
            })
          this.chartDataC = charts
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }
  // Get the sales ranking of dishes within a certain date
  private getTimeQuantumDishesDataes() {
    getTimeQuantumDishes({ start: this.stateTime, end: this.endTime })
      .then((res) => {
        if (res.data.code == 200) {
          const { data } = res.data
          let yData: number[] = []
          data.series.length > 0 &&
            data.series.map((n: number) => {
              yData.push(n / 100)
            })
          const charts = { xData: data.xaxis, yData: yData }
          this.chartDataB = charts
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch((err) => {
        this.$message.error('Request error: ' + err.message)
      })
  }
}
</script>
<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
    .headBut {
      span {
        cursor: pointer;
        margin: 0 10px;
        font-size: 18px;
        padding: 16px 0px;
      }
      .act {
        border-bottom: solid 4px $mine;
        font-weight: bold;
      }
    }
    .topLable {
      font-size: 16px;
      background: #fff;
      position: relative;
      z-index: 1;
      padding: 15px 28px;
      margin-bottom: 30px;
      border-radius: 4px;
      .tit {
        line-height: 40px;
        color: $mine;
      }
      .dataSelect {
        display: flex;
        justify-content: space-between;
        line-height: 40px;
        div:first-child {
          span:first-child {
            margin-right: 10px;
            cursor: pointer;
          }
          span:last-child {
            margin-left: 10px;
            cursor: pointer;
          }
        }
      }
      .but {
        background: $mine;
        cursor: pointer;
        display: inline-block;
        color: #fff;
        padding: 0px 20px;
        line-height: 30px;
        height: 30px;
        border-radius: 4px;
        font-size: 14px;
      }
    }
    .container {
      background: #fff;
      position: relative;
      z-index: 1;
      padding: 30px 28px;
      border-radius: 4px;
      margin-bottom: 30px;
      .topDataBox {
        display: flex;
        margin-bottom: 15px;
        .icon {
          width: 80px;
          height: 80px;
          /*padding-top: 15px;*/
          border-radius: 100%;
          background: #fff;
          margin-right: 10px;
          text-align: center;
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .box {
          margin-right: 15px;
          display: flex;
          padding: 15px;
          flex: 1;
          border-radius: 5px;
          color: #fff;
          .item {
            div:nth-child(2) {
              font-size: 18px;
              line-height: 36px;
            }
          }
        }
        .nowData {
          background: linear-gradient(
            63deg,
            rgba(85, 169, 255, 1) 24%,
            rgba(55, 154, 255, 1) 100%
          );
        }
        .noData {
          background: linear-gradient(
            63deg,
            rgba(255, 168, 104, 1) 33%,
            rgba(255, 144, 61, 1) 100%
          );
        }
        .employee {
          margin-right: 0;
          background: linear-gradient(
            121deg,
            rgba(255, 136, 136, 1) 24%,
            rgba(245, 108, 108, 1) 100%
          );
        }
      }
      .chartBox {
        display: flex;
        div {
          flex: 1;
        }
        .itemList {
          .title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 20px;
          }
          .item {
            display: flex;
            line-height: 45px;
            padding: 0 20px;
            border-bottom: solid 1px #f0f1f4;
            span {
              flex: 1;
              text-align: center;
            }
            span:first-child {
              text-align: left;
            }
          }
          .topLab {
            background: #f0f1f4;
            border-radius: 4px;
          }
        }
      }
    }
    .topButBox {
      position: absolute;
      right: 20px;
      top: 0;
      z-index: 9999;
      font-size: 12px;
      span {
        display: inline-block;
        padding: 4px 10px;
        border-radius: 4px;
        cursor: pointer;
      }
      .butAct {
        background: #3a9bff;
        color: #fff;
      }
    }
  }
}
</style>

<template>
  <div class="container">
    <h2 class="homeTitle">Order statistics</h2>
    <div class="charBox">
      <div class="orderProportion">
        <div>
          <p>Order completion rate</p>
          <p>{{ (orderdata.orderCompletionRate * 100).toFixed(1) }}%</p>
        </div>
        <div class="symbol">=</div>
        <div>
          <p>Valid orders</p>
          <p>{{ orderdata.validOrderCount }}</p>
        </div>
        <div class="symbol">/</div>
        <div>
          <p>Total orders</p>
          <p>{{ orderdata.totalOrderCount }}</p>
        </div>
      </div>
      <div id="ordermain" style="width: 100%; height: 300px"></div>
      <ul class="orderListLine">
        <li class="one"><span></span>Total orders (pcs)</li>
        <li class="three"><span></span>Valid orders (pcs)</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-property-decorator'
import * as echarts from 'echarts'
@Component({
  name: 'OrderStatistics',
})
export default class extends Vue {
  @Prop() private orderdata!: any
  @Prop() private overviewData!: any

  @Watch('orderdata')
  getData() {
    this.$nextTick(() => {
      this.initChart()
    })
  }
  initChart() {
    type EChartsOption = echarts.EChartsOption
    const chartDom = document.getElementById('ordermain') as any
    const myChart = echarts.init(chartDom)
    // // Loop through the x-axis data
    // const baseDate = this.orderdata.list.map((item) => {
    //   return (item as any).date
    // })
    // const baseAmount = this.orderdata.list.map((item) => {
    //   return (item as any).amount
    // })
    // const baseValidNum = this.orderdata.list.map((item) => {
    //   return (item as any).accomplishNum
    // })
    // const baseAccomplishNum = this.orderdata.list.map((item) => {
    //   return (item as any).accomplishNum
    // })
    console.log(this.orderdata)
    var option: any
    option = {
      // legend: {
      //   itemHeight: 3, //Legend height
      //   itemWidth: 12, //Legend width
      //   icon: 'rect', //Legend
      //   show: true,
      //   top: 'bottom',
      //   data: ['Order completion rate', 'Valid orders', 'Total orders'],
      // },
      tooltip: {
        trigger: 'axis',
        backgroundColor: '#fff', //Background color (at this time, the default color)
        borderRadius: 2, //Border radius
        textStyle: {
          color: '#333', //Font color
          fontSize: 12, //Font
          fontWeight: 300,
        },
      },
      grid: {
        top: '5%',
        left: '20',
        right: '50',
        bottom: '12%',
        containLabel: true,
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        axisLabel: {
          //X-axis font color
          textStyle: {
            color: '#666',
            fontSize: '12px',
          },
        },
        axisLine: {
          //X-axis line color
          lineStyle: {
            color: '#E5E4E4',
            width: 1, //Width of x-axis line
          },
        },
        data: this.orderdata.data.dateList, //Dynamic data from the backend
      },
      yAxis: [
        {
          type: 'value',
          min: 0,
          //max: 500,
          interval: 50,
          axisLabel: {
            textStyle: {
              color: '#666',
              fontSize: '12px',
            },
            // formatter: "{value} ml",//Unit
          },
        }, //Left value
      ],
      series: [
        {
          name: 'Total orders',
          type: 'line',
          // stack: 'Total',
          smooth: false, //No smooth curve
          showSymbol: false, //Not displayed when the mouse moves up
          symbolSize: 10,
          // symbol:"circle", //Set the position of the line point
          itemStyle: {
            normal: {
              color: '#FFD000',
              lineStyle: {
                color: '#FFD000',
              },
            },
            emphasis: {
              color: '#fff',
              borderWidth: 5,
              borderColor: '#FFC100',
            },
          },

          data: this.orderdata.data.orderCountList,
        },
        {
          name: 'Valid orders',
          type: 'line',
          // stack: 'Total',
          smooth: false, //No smooth curve
          showSymbol: false, //Not displayed when the mouse moves up
          symbolSize: 10, //Point size
          // symbol:"circle", //Set the position of the line point
          itemStyle: {
            normal: {
              color: '#FD7F7F',
              lineStyle: {
                color: '#FD7F7F',
              },
            },
            emphasis: {
              // Point color
              color: '#fff',
              borderWidth: 5,
              borderColor: '#FD7F7F',
            },
          },

          data: this.orderdata.data.validOrderCountList,
        }
      ],
    }
    option && myChart.setOption(option)
  }
}
</script>

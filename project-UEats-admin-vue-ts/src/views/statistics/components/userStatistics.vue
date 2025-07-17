<template>
  <div class="container">
    <h2 class="homeTitle">User statistics</h2>
    <div class="charBox">
      <div id="usermain" style="width: 100%; height: 320px"></div>
      <ul class="orderListLine user">
        <li class="one"><span></span>Total users (pcs)</li>
        <li class="three"><span></span>New users (pcs)</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-property-decorator'
import * as echarts from 'echarts'
@Component({
  name: 'UserStatistics',
})
export default class extends Vue {
  @Prop() private userdata!: any
  @Watch('userdata')
  getData() {
    this.$nextTick(() => {
      this.initChart()
    })
  }
  initChart() {
    type EChartsOption = echarts.EChartsOption
    const chartDom = document.getElementById('usermain') as any
    const myChart = echarts.init(chartDom)
    var option: any
    option = {
      // legend: {
      //   itemHeight: 3, //Legend height
      //   itemWidth: 12, //Legend width
      //   icon: 'rect', //Legend
      //   show: true,
      //   top: 'bottom',
      //   data: ['Total users', 'New users'],
      // },
      tooltip: {
        trigger: 'axis',
        backgroundColor: '#fff', //Background color (at this time, the default color)
        borderRadius: 2, //Border radius
        textStyle: {
          color: '#333', //Font color
          fontSize: 12, //Font size
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
        data: this.userdata.dateList, //Dynamic data from the backend
      },
      yAxis: [
        {
          type: 'value',
          min: 0,
          //max: 500,
          //interval: 100,
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
          name: 'Total users',
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

          data: this.userdata.totalUserList,
        },
        {
          name: 'New users',
          type: 'line',
          // stack: 'Total',
          smooth: false, //No smooth curve
          showSymbol: false, //Not displayed when the mouse moves up
          symbolSize: 10, //Point size
          // symbol:"circle", //Set the position of the line point
          itemStyle: {
            normal: {
              color: '#FD7F7F',
              fontWeigth: 300,
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

          data: this.userdata.newUserList,
        },
      ],
    }
    option && myChart.setOption(option)
  }
}
</script>
<style scoped>
</style>

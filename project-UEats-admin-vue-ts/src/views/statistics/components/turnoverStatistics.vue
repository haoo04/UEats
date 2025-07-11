<template>
  <div class="container">
    <h2 class="homeTitle">Turnover statistics</h2>
    <div class="charBox">
      <div id="main" style="width: 100%; height: 320px"></div>
      <ul class="orderListLine turnover">
        <li>Turnover (RM)</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-property-decorator'
import * as echarts from 'echarts'
@Component({
  name: 'TurnoverStatistics',
})
export default class extends Vue {
  @Prop() private turnoverdata!: any
  @Watch('turnoverdata')
  getData() {
    this.$nextTick(() => {
      this.initChart()
    })
  }
  initChart() {
    type EChartsOption = echarts.EChartsOption
    const chartDom = document.getElementById('main') as any
    const myChart = echarts.init(chartDom)

    var option: any
    option = {
      // title: {
      //   text: 'Turnover (RM)',
      //   top: 'bottom',
      //   left: 'center',
      //   textAlign: 'center',
      //   textStyle: {
      //     fontSize: 12,
      //     fontWeight: 'normal',
      //   },
      // },
      tooltip: {
        trigger: 'axis',
      },
      grid: {
        top: '5%',
        left: '10',
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
        data: this.turnoverdata.dateList, //Dynamic data from the backend
      },
      yAxis: [
        {
          type: 'value',
          min: 0,
          //max: 50000,
          //interval: 1000,
          axisLabel: {
            textStyle: {
              color: '#666',
              fontSize: '12px',
            }
            // formatter: "{value} ml",//Unit
          }
        }
      ],
      series: [
        {
          name: 'Turnover',
          type: 'line',
          // stack: 'Total',
          smooth: false, //No smooth curve
          showSymbol: false, //Not displayed when the mouse moves up
          symbolSize: 10,
          // symbol:"circle", //Set the position of the line point
          itemStyle: {
            normal: {
              color: '#F29C1B',
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

          data: this.turnoverdata.turnoverList,
        },
      ],
    }
    option && myChart.setOption(option)
  }
}
</script>

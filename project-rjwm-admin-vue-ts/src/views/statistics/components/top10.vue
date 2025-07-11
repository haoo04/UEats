<template>
  <div class="container top10">
    <h2 class="homeTitle">Top 10 sales</h2>
    <div class="charBox">
      <div id="top" style="width: 100%; height: 380px"></div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-property-decorator'
import * as echarts from 'echarts'
@Component({
  name: 'Top',
})
export default class extends Vue {
  @Prop() private top10data!: any
  @Watch('top10data')
  getData() {
    this.$nextTick(() => {
      this.initChart()
    })
  }
  initChart() {
    type EChartsOption = echarts.EChartsOption
    const chartDom = document.getElementById('top') as any
    const myChart = echarts.init(chartDom)
    var option: any
    option = {
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
        top: '-10px',
        left: '0',
        right: '0',
        bottom: '0',
        containLabel: true,
      },
      xAxis: {
        show: false,
      },
      yAxis: {
        // Hide y-axis coordinates
        axisLine: {
          show: false,
        },
        // Hide y-axis scale lines
        axisTick: {
          show: false,
          alignWithLabel: true,
        },
        type: 'category',
        // interval: 100,
        axisLabel: {
          textStyle: {
            color: '#666',
            fontSize: '12px',
          },
          // formatter: "{value} ml",//Unit
        },
        data: this.top10data.nameList,
      },
      series: [
        {
          data: this.top10data.numberList,
          type: 'bar',
          showBackground: true,
          backgroundStyle: {
            color: '#F3F4F7',
          },
          barWidth: 20,
          barGap: '80%' /*Set the spacing between multiple parallel columns*/,
          barCategoryGap: '80%' /*Set the spacing between multiple parallel columns*/,

          itemStyle: {
            emphasis: {
              barBorderRadius: 30,
            },
            normal: {
              barBorderRadius: [0, 10, 10, 0], // Corner radius
              color: new echarts.graphic.LinearGradient( // Gradient color
                1,
                0,
                0,
                0, // Start and end positions of gradient color, right/down/left/up
                [
                  // Offset position
                  { offset: 0, color: '#FFBD00' },
                  { offset: 1, color: '#FFD000' },
                ]
              ),
              label: {
                //Content style
                show: true,
                formatter: '{@score}',
                color: '#333',
                // position: "insideLeft", //Internal left alignment
                position: ['8', '5'], //Custom position, the first parameter is the x-axis direction, the second parameter is the y-axis direction, the upper left corner is the starting point, the right and down are positive numbers, and the upper left are negative numbers
              },
            },
          },
          // label: {
          //   show: true,
          //   position: "left",
          //   valueAnimation: true,
          // },
        },
      ],
    }
    option && myChart.setOption(option)
  }
}
</script>

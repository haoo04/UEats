<template>
  <div class="dashboard-container">
    <div class="informBox">
      <ul class="conTab">
        <li
          v-for="(item, index) in tabList"
          :key="index"
          :class="activeIndex === index ? 'active' : ''"
          @click="handleClass(index)"
        >
          <el-badge
            class="item"
            :class="ountUnread >= 10 ? 'badgeW' : ''"
            :value="
              ountUnread === 0 ? null : ountUnread > 99 ? '99+' : ountUnread
            "
            :hidden="!([1].includes(item.value) && ountUnread)"
            >{{ item.label }}</el-badge
          >
        </li>
      </ul>

      <el-button
        icon="iconfont icon-clear"
        class="right-el-button"
        v-if="status === 1 && baseData.length > 0"
        @click="handleBatch"
        >Mark All as Read</el-button
      >
      <el-button
        icon="iconfont icon-clear"
        class="right-el-button onbutton"
        disabled
        v-else
        >Mark All as Read</el-button
      >
    </div>
    <div class="container newBox" :class="{ hContainer: baseData.length }">
      <div class="informList" v-if="baseData.length > 0">
        <div v-for="(item, index) in baseData" :key="index">
          <!-- Pending Order -->
          <div class="item" v-if="item.type === 1">
            <div class="tit">
              <span>[Pending]</span>{{ item.arrNew[0]
              }}<span class="fontOrderTip" @click="handleSetStatus(item.id)">
                <router-link :to="'/order?status=' + 2">{{
                  item.arrNew[1]
                }}</router-link></span
              >{{ item.arrNew[2]
              }}<span class="time">{{ item.createTime }}</span>
            </div>
          </div>
          <div class="item" v-if="item.type === 2">
            <div class="tit">
              <i>Urgent</i><span>[Pending]</span>{{ item.arrNew[0]
              }}<span class="fontOrderTip" @click="handleSetStatus(item.id)"
                ><router-link :to="'/order?status=' + 2">{{
                  item.arrNew[1]
                }}</router-link></span
              >{{ item.arrNew[2]
              }}<span class="time">{{ item.createTime }}</span>
            </div>
          </div>
          <!-- end -->
          <!-- To be Dispatched -->
          <div class="item" v-if="item.type === 3">
            <div class="tit">
              <span>[To be Dispatched]</span>{{ item.arrNew[0]
              }}<span class="fontOrderTip" @click="handleSetStatus(item.id)"
                ><router-link :to="'/order?status=' + 2">{{
                  item.arrNew[1]
                }}</router-link></span
              >{{ item.arrNew[2]
              }}<span class="time">{{ item.createTime }}</span>
            </div>
          </div>
          <!-- end -->
          <!-- Reminder -->
          <div
            class="item"
            v-if="item.type === 4"
            @mouseenter="toggleShow(item.id, index)"
            @mouseleave="mouseLeaves(index)"
          >
            <div :class="isActive ? 'titAlready' : ''">
              <div class="tit">
                <span>[Reminder]</span>{{ item.arrNew[0] }}
                <span class="time">{{ item.createTime }}</span>
              </div>
              <div v-if="shopShow && showIndex === index" class="orderInfo">
                <p>
                  <span
                    ><label>Order Time:</label>{{ item.details.orderTime }}</span
                  ><span
                    ><label>Estimated Delivery Time:</label
                    >{{ item.details.estimatedDeliveryTime }}</span
                  >
                </p>
                <p>
                  {{ item.details.consignee }}, {{ item.details.phone }}, {{
                    item.details.address
                  }}
                </p>
                <p>
                  <span
                    ><label>Dishes:</label>{{ item.details.orderDishes }}</span
                  >
                </p>
              </div>
            </div>
          </div>
          <!-- end -->
          <!-- Today's Data -->
          <div
            class="item"
            v-if="item.type === 5"
            @mouseenter="toggleShow(item.id, index)"
            @mouseleave="mouseLeaves(index)"
          >
            <div :class="isActive ? 'titAlready' : ''">
              <div class="tit">
                <span>[Today's Data]</span>Work hard, but also live well.<span
                  class="time"
                  >{{ item.createTime }}</span
                >
              </div>
              <div v-if="shopShow && showIndex === index" class="orderInfo">
                <p>
                  <span
                    ><label>Turnover:</label>{{ item.details.turnover }}</span
                  >
                  <span
                    ><label>Valid Orders:</label
                    >{{ item.details.validOrderCount }}</span
                  >
                  <span
                    ><label>Order Completion Rate:</label
                    >{{ item.details.orderCompletionRate }}</span
                  >
                </p>
                <p>
                  <span
                    ><label>New Users Today:</label
                    >{{ item.details.newUsers }}</span
                  >
                  <span
                    ><label>Cancelled Today:</label
                    >{{ item.details.cancelledOrders }}</span
                  >
                  <span
                    ><label>Cancelled Amount Today:</label>￥{{
                      item.details.cancelledAmount
                    }}</span
                  >
                </p>
              </div>
            </div>
          </div>
        </div>
        <!-- end -->
      </div>
      <Empty v-else :is-search="isSearch" />
      <el-pagination
        v-if="counts > 10"
        class="pageList"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="counts"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Inject } from 'vue-property-decorator'
import Empty from '@/components/Empty/index.vue'
import { getNewData, setNewData } from '@/utils/cookies'
import { AppModule } from '@/store/modules/app'
// 接口
import {
  getInformData,
  batchMsg,
  setStatus,
  getCountUnread,
} from '@/api/inform'
@Component({
  name: 'Inform',
  components: {
    Empty,
  },
})
export default class extends Vue {
  // @Inject('reload') readonly reload!: Function
  private activeIndex = 0
  private shopShow = false
  private counts: number = 0
  private page: number = 1
  private pageSize: number = 10
  private status = 1
  private baseData = []
  // private ountUnread = 0 as any
  private showIndex = 0
  private isSearch: boolean = false
  private isActive: boolean = false

  get tabList() {
    return [
      {
        label: 'Unread',
        value: 1,
      },
      {
        label: 'Read',
        value: 2,
      },
    ]
  }
  get ountUnread() {
    return AppModule.statusNumber
  }
  created() {
    this.getData()
  }
  // Get list data
  async getData() {
    const parent = {
      pageNum: this.page,
      pageSize: this.pageSize,
      status: this.status,
    }
    const { data } = await getInformData(parent)
    if (data.code === 1) {
      this.baseData = data.data.records
      this.counts = data.data.total
      let objNew = {} as any
      let arrDetails = []
      this.baseData.forEach((val) => {
        // Process the escaped order string returned from the backend
        const arrContent = val.content.split(' ')
        // Process reminder and closing details data
        val.arrNew = arrContent
        objNew = { ...val }
        objNew.details = eval('(' + objNew.details + ')')
        arrDetails.push(objNew)
      })

      this.baseData = arrDetails
    } else {
      this.$message.error(data.msg)
    }
  }

  // Mark all as read
  async handleBatch() {
    const ids = []
    this.baseData.forEach((val) => {
      ids.push(val.id)
    })
    const { data } = await batchMsg(ids)
    if (data.code === 1) {
      this.getCountUnread()
      this.getData()
    } else {
      this.$message.error(data.msg)
    }
  }
  // Set single order as read
  async handleSetStatus(id) {
    const { data } = await setStatus(id)
    if (data.code === 1) {
      if (!this.isActive) {
        this.getCountUnread()
        this.getData()
      }
    } else {
      this.$message.error(data.msg)
    }
  }
  // Get unread messages
  async getCountUnread() {
    const { data } = await getCountUnread()
    if (data.code === 1) {
      AppModule.StatusNumber(data.data)
    } else {
      this.$message.error(data.msg)
    }
  }
  // Trigger read/unread button
  handleClass(index) {
    this.activeIndex = index
    if (index === 0) {
      this.status = 1
    } else {
      this.status = 2
    }
    this.getData()
  }
  // Dropdown menu display
  toggleShow(id, index) {
    this.shopShow = true
    this.showIndex = index
    let t = 3
    let timer = setInterval(() => {
      t--
      if (t === 0) {
        if (this.status === 1) {
          this.isActive = true
          this.handleSetStatus(id)
        }

        clearInterval(timer)
      }
    }, 1000)
  }
  // Dropdown menu hide
  mouseLeaves(index) {
    this.shopShow = false
    this.showIndex = index
  }
  private handleSizeChange(val: any) {
    this.pageSize = val
    this.getData()
  }

  private handleCurrentChange(val: any) {
    this.page = val
    this.getData()
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
    .container {
      background: #fff;
      position: relative;
      z-index: 1;
      padding: 0 30px;
      border-radius: 4px;
      // min-height: 650px;
      height: calc(100% - 55px);
      overflow: hidden;
      &.newBox {
        // padding:0;
        .pageList {
          border-top: 1px solid #f3f4f7;
          padding: 40px;
          margin-top: 0;
        }
      }
    }
    .hContainer {
      height: auto !important;
    }
  }
}
</style>
<template>
  <div class="dashboard-container">
    <TabChange
      :order-statics="orderStatics"
      :default-activity="defaultActivity"
      @tabChange="change"
    />
    <div class="container" :class="{ hContainer: tableData.length }">
      <!-- Search items -->
      <div class="tableBar">
        <label style="margin-right: 10px">Order Number：</label>
        <el-input
          v-model="input"
          placeholder="Please enter order number"
          style="width: 15%"
          clearable
          @clear="init(orderStatus)"
          @keyup.enter.native="initFun(orderStatus)"
        />
        <label style="margin-left: 20px">Phone Number：</label>
        <el-input
          v-model="phone"
          placeholder="Please enter phone number"
          style="width: 15%"
          clearable
          @clear="init(orderStatus)"
          @keyup.enter.native="initFun(orderStatus)"
        />
        <label style="margin-left: 20px">Order Time：</label>
        <el-date-picker
          v-model="valueTime"
          clearable
          value-format="yyyy-MM-dd HH:mm:ss"
          range-separator="To"
          :default-time="['00:00:00', '23:59:59']"
          type="daterange"
          start-placeholder="Start Date"
          end-placeholder="End Date"
          style="width: 25%; margin-left: 10px"
          @clear="init(orderStatus)"
        />
        <el-button class="normal-btn continue" @click="init(orderStatus, true)">
          Search
        </el-button>
      </div>
      <el-table
        v-if="tableData.length"
        :data="tableData"
        stripe
        class="tableBox"
      >
        <el-table-column key="number" prop="number" label="Order Number" />
        <el-table-column
          v-if="[2, 3, 4].includes(orderStatus)"
          key="orderDishes"
          prop="orderDishes"
          label="Order Dishes"
        />
        <el-table-column
          v-if="[0].includes(orderStatus)"
          key="status"
          prop="Order Status"
          label="Order Status"
        >
          <template slot-scope="{ row }">
            <span>{{ getOrderType(row) }}</span>
          </template>
        </el-table-column>
        <el-table-column
          v-if="[0, 5, 6].includes(orderStatus)"
          key="consignee"
          prop="consignee"
          label="User Name"
          show-overflow-tooltip
        />
        <el-table-column
          v-if="[0, 5, 6].includes(orderStatus)"
          key="phone"
          prop="phone"
          label="Phone Number"
        />
        <el-table-column
          v-if="[0, 2, 3, 4, 5, 6].includes(orderStatus)"
          key="address"
          prop="address"
          label="Address"
          :class-name="orderStatus === 6 ? 'address' : ''"
        />
        <el-table-column
          v-if="[0, 6].includes(orderStatus)"
          key="orderTime"
          prop="orderTime"
          label="Order Time"
          class-name="orderTime"
          min-width="110"
        />
        <el-table-column
          v-if="[6].includes(orderStatus)"
          key="cancelTime"
          prop="cancelTime"
          class-name="cancelTime"
          label="Cancel Time"
          min-width="110"
        />
        <el-table-column
          v-if="[6].includes(orderStatus)"
          key="cancelReason"
          prop="cancelReason"
          label="Cancel Reason"
          class-name="cancelReason"
          :min-width="[6].includes(orderStatus) ? 80 : 'auto'"
        />
        <el-table-column
          v-if="[5].includes(orderStatus)"
          key="deliveryTime"
          prop="deliveryTime"
          label="Delivery Time"
        />
        <el-table-column
          v-if="[2, 3, 4].includes(orderStatus)"
          key="estimatedDeliveryTime"
          prop="estimatedDeliveryTime"
          label="Estimated Delivery Time"
          min-width="110"
        />
        <el-table-column
          v-if="[0, 2, 5].includes(orderStatus)"
          key="amount"
          prop="amount"
          label="Actual Receipt Amount" 
          align="center"
        >
          <template slot-scope="{ row }">
            <span>￥{{ (row.amount.toFixed(2) * 100) / 100 }}</span>
          </template>
        </el-table-column>
        <el-table-column
          v-if="[2, 3, 4, 5].includes(orderStatus)"
          key="remark"
          prop="remark"
          label="Remark"
          align="center"
        />
        <el-table-column
          v-if="[2, 3, 4].includes(orderStatus)"
          key="tablewareNumber"
          prop="tablewareNumber"
          label="Tableware Number"
          align="center"
          min-width="80"
        />
        <el-table-column
          prop="btn"
          label="Operation"
          align="center"
          :class-name="orderStatus === 0 ? 'operate' : 'otherOperate'"
          :min-width="
            [2, 3, 4].includes(orderStatus)
              ? 130
              : [0].includes(orderStatus)
              ? 140
              : 'auto'
          "
        >
          <template slot-scope="{ row }">
            <!-- <el-divider direction="vertical" /> -->
            <div class="before">
              <el-button
                v-if="row.status === 2"
                type="text"
                class="blueBug"
                @click="orderAccept(row), (isTableOperateBtn = true)"
              >
                Accept Order
              </el-button>
              <el-button
                v-if="row.status === 3"
                type="text"
                class="blueBug"
                @click="cancelOrDeliveryOrComplete(3, row.id)"
              >
                Delivery
              </el-button>
              <el-button
                v-if="row.status === 4"
                type="text"
                class="blueBug"
                @click="cancelOrDeliveryOrComplete(4, row.id)"
              >
                Complete
              </el-button>
            </div>
            <div class="middle">
              <el-button
                v-if="row.status === 2"
                type="text"
                class="delBut"
                @click="orderReject(row), (isTableOperateBtn = true)"
              >
                Reject Order
              </el-button>
              <el-button
                v-if="[1, 3, 4, 5].includes(row.status)"
                type="text"
                class="delBut"
                @click="cancelOrder(row)"
              >
                Cancel
              </el-button>
            </div>
            <div class="after">
              <el-button
                type="text"
                class="blueBug non"
                @click="goDetail(row.id, row.status, row)"
              >
                View
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
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

    <!-- View pop-up part -->
    <el-dialog
      title="Order Information"
      :visible.sync="dialogVisible"
      width="53%"
      :before-close="handleClose"
      class="order-dialog"
    >
      <el-scrollbar style="height: 100%">
        <div class="order-top">
          <div>
            <div style="display: inline-block">
              <label style="font-size: 16px">Order Number：</label>
              <div class="order-num">
                {{ diaForm.number }}
              </div>
            </div>
            <div
              style="display: inline-block"
              class="order-status"
              :class="{ status3: [3, 4].includes(dialogOrderStatus) }"
            >
              {{
                orderList.filter((item) => item.value === dialogOrderStatus)[0]
                  .label
              }}
            </div>
          </div>
          <p><label>Order Time：</label>{{ diaForm.orderTime }}</p>
        </div>

        <div class="order-middle">
          <div class="user-info">
            <div class="user-info-box">
              <div class="user-name">
                <label>User Name：</label>
                <span>{{ diaForm.consignee }}</span>
              </div>
              <div class="user-phone">
                <label>Phone Number：</label>
                <span>{{ diaForm.phone }}</span>
              </div>
              <div
                v-if="[2, 3, 4, 5].includes(dialogOrderStatus)"
                class="user-getTime"
              >
                <label>{{
                  dialogOrderStatus === 5 ? 'Delivery Time：' : 'Estimated Delivery Time：'
                }}</label>
                <span>{{
                  dialogOrderStatus === 5
                    ? diaForm.deliveryTime
                    : diaForm.estimatedDeliveryTime
                }}</span>
              </div>
              <div class="user-address">
                <label>Address：</label>
                <span>{{ diaForm.address }}</span>
              </div>
            </div>
            <div
              class="user-remark"
              :class="{ orderCancel: dialogOrderStatus === 6 }"
            >
              <div>{{ dialogOrderStatus === 6 ? 'Cancel Reason' : 'Remark' }}</div>
              <span>{{
                dialogOrderStatus === 6
                  ? diaForm.cancelReason || diaForm.rejectionReason
                  : diaForm.remark
              }}</span>
            </div>
          </div>

          <div class="dish-info">
            <div class="dish-label">Dishes</div>
            <div class="dish-list">
              <div
                v-for="(item, index) in diaForm.orderDetailList"
                :key="index"
                class="dish-item"
              >
                <div class="dish-item-box">
                  <span class="dish-name">{{ item.name }}</span>
                  <span class="dish-num">x{{ item.number }}</span>
                </div>
                <span class="dish-price"
                  >￥{{ item.amount ? item.amount.toFixed(2) : '' }}</span
                >
              </div>
            </div>
            <div class="dish-all-amount">
              <label>Dish Subtotal</label>
              <span
                >￥{{
                  (diaForm.amount - 6 - diaForm.packAmount).toFixed(2)
                }}</span
              >
            </div>
          </div>
        </div>

        <div class="order-bottom">
          <div class="amount-info">
            <div class="amount-label">Fee</div>
            <div class="amount-list">
              <div class="dish-amount">
                <span class="amount-name">Dish Subtotal：</span>
                <span class="amount-price"
                  >￥{{
                    ((diaForm.amount - 6 - diaForm.packAmount).toFixed(2) *
                      100) /
                    100
                  }}</span
                >
              </div>
              <div class="send-amount">
                <span class="amount-name">Delivery Fee：</span>
                <span class="amount-price">RM{{ 6 }}</span>
              </div>
              <div class="package-amount">
                <span class="amount-name">Pack Fee：</span>
                <span class="amount-price"
                  >RM{{
                    diaForm.packAmount
                      ? (diaForm.packAmount.toFixed(2) * 100) / 100
                      : ''
                  }}</span
                >
              </div>
              <div class="all-amount">
                <span class="amount-name">Total：</span>
                <span class="amount-price"
                  >RM{{
                    diaForm.amount
                      ? (diaForm.amount.toFixed(2) * 100) / 100
                      : ''
                  }}</span
                >
              </div>
              <div class="pay-type">
                <span class="pay-name">Payment Channel：</span>
                <span class="pay-value">{{
                  diaForm.payMethod === 1 ? 'WeChat Payment' : 'Alipay Payment'
                }}</span>
              </div>
              <div class="pay-time">
                <span class="pay-name">Payment Time：</span>
                <span class="pay-value">{{ diaForm.checkoutTime }}</span>
              </div>
            </div>
          </div>
        </div>
      </el-scrollbar>
      <span v-if="dialogOrderStatus !== 6" slot="footer" class="dialog-footer">
        <el-checkbox
          v-if="dialogOrderStatus === 2 && orderStatus === 2"
          v-model="isAutoNext"
          >After processing, automatically jump to the next one</el-checkbox
        >
        <el-button
          v-if="dialogOrderStatus === 2"
          @click="orderReject(row), (isTableOperateBtn = false)"
          >Reject Order</el-button
        >
        <el-button
          v-if="dialogOrderStatus === 2"
          type="primary"
          @click="orderAccept(row), (isTableOperateBtn = false)"
          >Accept Order</el-button
        >

        <el-button
          v-if="[1, 3, 4, 5].includes(dialogOrderStatus)"
          @click="dialogVisible = false"
          >Back</el-button
        >
        <el-button
          v-if="dialogOrderStatus === 3"
          type="primary"
          @click="cancelOrDeliveryOrComplete(3, row.id)"
          >Delivery</el-button
        >
        <el-button
          v-if="dialogOrderStatus === 4"
          type="primary"
          @click="cancelOrDeliveryOrComplete(4, row.id)"
          >Complete</el-button
        >
        <el-button
          v-if="[1].includes(dialogOrderStatus)"
          type="primary"
          @click="cancelOrder(row)"
          >Cancel Order</el-button
        >
      </span>
    </el-dialog>
    <!-- Reject order, cancel pop-up -->
    <el-dialog
      :title="cancelDialogTitle + 'Reason'"
      :visible.sync="cancelDialogVisible"
      width="42%"
      :before-close="() => ((cancelDialogVisible = false), (cancelReason = ''))"
      class="cancelDialog"
    >
      <el-form label-width="90px">
        <el-form-item :label="cancelDialogTitle + 'Reason：'">
          <el-select
            v-model="cancelReason"
            :placeholder="'Please select' + cancelDialogTitle + 'Reason'"
          >
            <el-option
              v-for="(item, index) in cancelDialogTitle === 'Cancel'
                ? cancelrReasonList
                : cancelOrderReasonList"
              :key="index"
              :label="item.label"
              :value="item.label"
            />
          </el-select>
        </el-form-item>
        <el-form-item v-if="cancelReason === 'Custom Reason'" label="Reason：">
          <el-input
            v-model.trim="remark"
            type="textarea"
            :placeholder="'Please enter your' + cancelDialogTitle + 'Reason（Limited to 20 characters）'"
            maxlength="20"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click=";(cancelDialogVisible = false), (cancelReason = '')"
          >Cancel</el-button
        >
        <el-button type="primary" @click="confirmCancel">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import InputAutoComplete from '@/components/InputAutoComplete/index.vue'
import TabChange from './tabChange.vue'
import Empty from '@/components/Empty/index.vue'
import {
  getOrderDetailPage,
  queryOrderDetailById,
  completeOrder,
  deliveryOrder,
  orderCancel,
  orderReject,
  orderAccept,
  getOrderListBy,
} from '@/api/order'

@Component({
  components: {
    HeadLable,
    InputAutoComplete,
    TabChange,
    Empty,
  },
})
export default class extends Vue {
  private defaultActivity: any = 0
  private orderStatics = {}
  private row = {}
  private isAutoNext = true
  private isTableOperateBtn = true
  private currentPageIndex = 0 //Record the index of the data viewed in detail
  private orderId = '' //Order number
  private input = '' //Search order number
  private phone = '' //Search phone number
  private valueTime = []
  private dialogVisible = false //Detail pop-up
  private cancelDialogVisible = false //Cancel, reject order pop-up
  private cancelDialogTitle = '' //Cancel, reject order pop-up title
  private cancelReason = ''
  private remark = '' //Custom reason
  private counts: number = 0
  private page: number = 1
  private pageSize: number = 10
  private tableData = []
  private diaForm = []
  private isSearch: boolean = false
  private orderStatus = 0 //List field display required order status, used to request data for paging
  private dialogOrderStatus = 0 //Popup required order status, used to display fields in detail
  private cancelOrderReasonList = [
    {
      value: 1,
      label: 'Order quantity is too large, temporarily unable to accept orders',
    },
    {
      value: 2,
      label: 'Dishes have been sold out, temporarily unable to accept orders',
    },
    {
      value: 3,
      label: 'Restaurant is closed, temporarily unable to accept orders',
    },
    {
      value: 0,
      label: 'Custom reason',
    },
  ]

  private cancelrReasonList = [
    {
      value: 1,
      label: 'Order quantity is too large, temporarily unable to accept orders',
    },
    {
      value: 2,
      label: 'Dishes have been sold out, temporarily unable to accept orders',
    },
    {
      value: 3,
      label: 'Rider shortage, unable to deliver',
    },
    {
      value: 4,
      label: 'Customer phone number cancelled',
    },
    {
      value: 0,
      label: 'Custom reason',
    },
  ]
  private orderList = [
    {
      label: 'All orders',
      value: 0,
    },
    {
      label: 'Pending Payment',
      value: 1,
    },
    {
      label: 'Pending Acceptance',
      value: 2,
    },
    {
      label: 'Pending Delivery',
      value: 3,
    },
    {
      label: 'Delivery in progress',
      value: 4,
    },
    {
      label: 'Completed',
      value: 5,
    },
    {
      label: 'Cancelled',
      value: 6,
    },
  ]

  created() {
    this.init(Number(this.$route.query.status) || 0)
  }

  mounted() {
    //If there is a value, it means that it is clicked by the message notification
    if (
      this.$route.query.orderId &&
      this.$route.query.orderId !== 'undefined'
    ) {
      this.goDetail(this.$route.query.orderId, 2)
    }
    if (this.$route.query.status) {
      this.defaultActivity = this.$route.query.status
    }
    // console.log(this.$route.query, 'this.$route')
  }

  initFun(orderStatus) {
    this.page = 1
    this.init(orderStatus)
  }

  change(activeIndex) {
    if (activeIndex === this.orderStatus) return
    this.init(activeIndex)
    this.input = ''
    this.phone = ''
    this.valueTime = []
    this.dialogOrderStatus = 0
    this.$router.push('/order')
    console.log(activeIndex, 'Received the index of the sub-component')
  }

  //Get the number of pending processing, pending delivery, and delivery in progress
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
        this.$message.error('Request failed：' + err.message)
      })
  }

  init(activeIndex: number = 0, isSearch?) {
    this.isSearch = isSearch
    const params = {
      page: this.page,
      pageSize: this.pageSize,
      number: this.input || undefined,
      phone: this.phone || undefined,
      beginTime:
        this.valueTime && this.valueTime.length > 0
          ? this.valueTime[0]
          : undefined,
      endTime:
        this.valueTime && this.valueTime.length > 0
          ? this.valueTime[1]
          : undefined,
      status: activeIndex || undefined,
    }
    getOrderDetailPage({ ...params })
      .then((res) => {
        if (res.data.code === 1) {
          this.tableData = res.data.data.records
          this.orderStatus = activeIndex
          this.counts = Number(res.data.data.total)
          this.getOrderListBy3Status()
          if (
            this.dialogOrderStatus === 2 &&
            this.orderStatus === 2 &&
            this.isAutoNext &&
            !this.isTableOperateBtn &&
            res.data.data.records.length > 1
          ) {
            const row = res.data.data.records[0]
            this.goDetail(row.id, row.status, row)
          } else {
            return null
          }
        } else {
          this.$message.error(res.data.msg)
        }
      })
      .catch((err) => {
        this.$message.error('Request failed：' + err.message)
      })
  }

  getOrderType(row: any) {
    if (row.status === 1) {
      return 'Pending Payment'
    } else if (row.status === 2) {
      return 'Pending Acceptance'
    } else if (row.status === 3) {
      return 'Pending Delivery'
    } else if (row.status === 4) {
      return 'Delivery in progress'
    } else if (row.status === 5) {
      return 'Completed'
    } else if (row.status === 6) {
      return 'Cancelled'
    } else {
      return 'Refund'
    }
  }

  //View details
  async goDetail(id: any, status: number, row?: any) {
    // console.log(111, index, row)
    this.diaForm = []
    this.dialogVisible = true
    this.dialogOrderStatus = status
    this.orderId = id
    const { data } = await queryOrderDetailById({ orderId: id })
    this.diaForm = data.data
    this.row = row || { id: this.$route.query.orderId, status: status }
    if (this.$route.query.orderId) {
      this.$router.push('/order')
    }
  }

  //Open the reject order pop-up
  orderReject(row: any) {
    this.cancelDialogVisible = true
    this.orderId = row.id
    this.dialogOrderStatus = row.status
    this.cancelDialogTitle = 'Reject'
    this.dialogVisible = false
    this.cancelReason = ''
  }

  //Accept order
  orderAccept(row: any) {
    this.orderId = row.id
    this.dialogOrderStatus = row.status
    orderAccept({ id: this.orderId })
      .then((res) => {
        if (res.data.code === 1) {
          this.$message.success('Operation successful')
          this.orderId = ''
          // this.dialogOrderStatus = 0
          this.dialogVisible = false
          this.init(this.orderStatus)
        } else {
          this.$message.error(res.data.msg)
        }
      })
      .catch((err) => {
        this.$message.error('Request failed：' + err.message)
      })
  }

  //Open the cancel order pop-up
  cancelOrder(row: any) {
    this.cancelDialogVisible = true
    this.orderId = row.id
    this.dialogOrderStatus = row.status
    this.cancelDialogTitle = 'Cancel'
    this.dialogVisible = false
    this.cancelReason = ''
  }

  //Confirm cancel or reject order and fill in the reason
  confirmCancel(type) {
    if (!this.cancelReason) {
      return this.$message.error(`Please select${this.cancelDialogTitle}Reason`)
    } else if (this.cancelReason === 'Custom reason' && !this.remark) {
      return this.$message.error(`Please enter${this.cancelDialogTitle}Reason`)
    }

    ;(this.cancelDialogTitle === 'Cancel' ? orderCancel : orderReject)({
      id: this.orderId,
      // eslint-disable-next-line standard/computed-property-even-spacing
      [this.cancelDialogTitle === 'Cancel' ? 'cancelReason' : 'rejectionReason']:
        this.cancelReason === 'Custom reason' ? this.remark : this.cancelReason,
    })
      .then((res) => {
        if (res.data.code === 1) {
          this.$message.success('Operation successful')
          this.cancelDialogVisible = false
          this.orderId = ''
          // this.dialogOrderStatus = 0
          this.init(this.orderStatus)
        } else {
          this.$message.error(res.data.msg)
        }
      })
      .catch((err) => {
        this.$message.error('Request failed：' + err.message)
      })
  }

  //Delivery, complete
  cancelOrDeliveryOrComplete(status: number, id: string) {
    const params = {
      status,
      id,
    }
    ;(status === 3 ? deliveryOrder : completeOrder)(params)
      .then((res) => {
        if (res.data.code === 1) {
          this.$message.success('Operation successful')
          this.orderId = ''
          // this.dialogOrderStatus = 0
          this.dialogVisible = false
          this.init(this.orderStatus)
        } else {
          this.$message.error(res.data.msg)
        }
      })
      .catch((err) => {
        this.$message.error('Request failed：' + err.message)
      })
  }

  handleClose() {
    this.dialogVisible = false
  }

  private handleSizeChange(val: any) {
    this.pageSize = val
    this.init(this.orderStatus)
  }

  private handleCurrentChange(val: any) {
    this.page = val
    this.init(this.orderStatus)
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
    // height: 100%;
    min-height: 700px;
    .container {
      background: #fff;
      position: relative;
      z-index: 1;
      padding: 30px 28px;
      border-radius: 4px;
      // min-height: 650px;
      height: calc(100% - 55px);

      .tableBar {
        // display: flex;
        margin-bottom: 20px;
        justify-content: space-between;

        .tableLab {
          span {
            cursor: pointer;
            display: inline-block;
            font-size: 14px;
            padding: 0 20px;
            color: $gray-2;
            border-right: solid 1px $gray-4;
          }
        }
      }

      .tableBox {
        width: 100%;
        border: 1px solid $gray-5;
        border-bottom: 0;
      }

      .pageList {
        text-align: center;
        margin-top: 30px;
      }
      //Search black button style
      .normal-btn {
        background: #333333;
        color: white;
        margin-left: 20px;
      }
    }
    .hContainer {
      height: auto !important;
    }
  }
}

.search-btn {
  margin-left: 20px;
}

.info-box {
  margin: -15px -44px 20px;
  p {
    display: flex;
    height: 20px;
    line-height: 20px;
    font-size: 14px;
    font-weight: 400;
    color: #666666;
    text-align: left;
    margin-bottom: 14px;
    &:last-child {
      margin-bottom: 0;
    }
    label {
      width: 100px;
      display: inline-block;
      color: #666;
    }
    .des {
      flex: 1;
      color: #333333;
    }
  }
}

.order-top {
  // height: 80px;
  border-bottom: 1px solid #e7e6e6;
  padding-bottom: 26px;
  padding-left: 22px;
  padding-right: 22px;
  // margin: 0 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .order-status {
    width: 57.25px;
    height: 27px;
    background: #333333;
    border-radius: 13.5px;
    color: white;
    margin-left: 19px;
    text-align: center;
    line-height: 27px;
  }
  .status3 {
    background: #f56c6c;
  }
  p {
    color: #333;
    label {
      color: #666;
    }
  }
  .order-num {
    font-size: 16px;
    color: #2a2929;
    font-weight: bold;
    display: inline-block;
  }
}

.order-middle {
  .user-info {
    min-height: 140px;
    background: #fbfbfa;
    margin-top: 23px;

    padding: 20px 43px;
    color: #333;
    .user-info-box {
      min-height: 55px;
      display: flex;
      flex-wrap: wrap;
      .user-name {
        flex: 67%;
      }
      .user-phone {
        flex: 33%;
      }
      .user-getTime {
        margin-top: 14px;
        flex: 80%;
        label {
          margin-right: 3px;
        }
      }
      label {
        margin-right: 17px;
        color: #666;
      }

      .user-address {
        margin-top: 14px;
        flex: 80%;
        label {
          margin-right: 30px;
        }
      }
    }
    .user-remark {
      min-height: 43px;
      line-height: 43px;
      background: #fffbf0;
      border: 1px solid #fbe396;
      border-radius: 4px;
      margin-top: 10px;
      padding: 6px;
      display: flex;
      align-items: center;
      div {
        display: inline-block;
        min-width: 53px;
        height: 32px;
        background: #fbe396;
        border-radius: 4px;
        text-align: center;
        line-height: 32px;
        color: #333;
        margin-right: 30px;
        // padding: 12px 6px;
      }
      span {
        color: #f2a402;
        line-height: 1.15;
      }
    }
    .orderCancel {
      background: #ffffff;
      border: 1px solid #b6b6b6;

      div {
        padding: 0 10px;
        background-color: #e5e4e4;
      }
      span {
        color: #f56c6c;
      }
    }
  }
  .dish-info {
    // min-height: 180px;
    display: flex;
    flex-wrap: wrap;
    padding: 20px 40px;
    border-bottom: 1px solid #e7e6e6;
    .dish-label {
      color: #666;
    }
    .dish-list {
      flex: 80%;
      display: flex;
      flex-wrap: wrap;
      .dish-item {
        flex: 50%;
        margin-bottom: 14px;
        color: #333;
        .dish-num {
        }
        .dish-item-box {
          display: inline-block;
          width: 120px;
        }
      }
    }
    .dish-label {
      margin-right: 65px;
    }
    .dish-all-amount {
      flex: 1;
      padding-left: 92px;
      margin-top: 10px;
      label {
        color: #333333;
        font-weight: bold;
        margin-right: 5px;
      }
      span {
        color: #f56c6c;
      }
    }
  }
}
.order-bottom {
  .amount-info {
    // min-height: 180px;
    display: flex;
    flex-wrap: wrap;
    padding: 20px 40px;
    padding-bottom: 0px;
    .amount-label {
      color: #666;
      margin-right: 65px;
    }
    .amount-list {
      flex: 80%;
      display: flex;
      flex-wrap: wrap;
      color: #333;
      // height: 65px;
      .dish-amount,
      .package-amount,
      .pay-type {
        display: inline-block;
        width: 300px;
        margin-bottom: 14px;
        flex: 50%;
      }
      .send-amount,
      .all-amount,
      .pay-time {
        display: inline-block;
        flex: 50%;
        padding-left: 10%;
      }
      .package-amount {
        .amount-name {
          margin-right: 14px;
        }
      }
      .all-amount {
        .amount-name {
          margin-right: 24px;
        }
        .amount-price {
          color: #f56c6c;
        }
      }
      .send-amount {
        .amount-name {
          margin-right: 10px;
        }
      }
    }
  }
}
</style>

<style lang="scss">
.dashboard-container {
  .cancelReason {
    padding-left: 40px;
  }
  .cancelTime {
    padding-left: 50px;
  }
  .orderTime {
    padding-left: 50px;
  }
  td.operate .cell {
    .before,
    .middle,
    .after {
      height: 39px;
      width: 48px;
    }
  }
  td.operate .cell,
  td.otherOperate .cell {
    display: flex;
    flex-wrap: nowrap;
    justify-content: center;
  }
  .order-dialog {
    .el-dialog {
      max-height: 764px !important;
      display: flex;
      flex-direction: column;
      margin: 0 !important;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      max-height: calc(100% - 30px);
      max-width: calc(100% - 30px);
    }
    .el-dialog__body {
      height: 520px !important;
    }
  }
}
.el-dialog__body {
  padding-top: 34px;
  padding-left: 30px;
  padding-right: 30px;
}
.cancelDialog {
  .el-dialog__body {
    padding-left: 64px;
  }
  .el-select,
  .el-textarea {
    width: 293px;
  }
  .el-textarea textarea {
    height: 114px;
  }
}
.el-dialog__footer {
  .el-checkbox {
    float: left;
    margin-left: 40px;
  }
  .el-checkbox__label {
    color: #333333 !important;
  }
}
.empty-box {
  display: flex;
  align-items: center;
  justify-content: center;
  img {
    margin-top: 0 !important;
  }
}
</style>

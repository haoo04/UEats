<template>
  <div class="navbar">
    <div class="statusBox">
      <hamburger id="hamburger-container"
                 :is-active="sidebar.opened"
                 class="hamburger-container"
                 @toggleClick="toggleSideBar" />
      <span v-if="status===1"
            class="businessBtn">Open</span>
      <span v-else
            class="businessBtn closing">Closed</span>
    </div>

    <div :key="restKey"
         class="right-menu">
      <div class="rightStatus">
        <audio ref="audioVo"
               hidden>
          <source src="./../../../assets/preview.mp3"
                  type="audio/mp3">
          </source>
        </audio>
        <audio ref="audioVo2"
               hidden>
          <source src="./../../../assets/reminder.mp3"
                  type="audio/mp3">
          </source>
        </audio>
        <span class="navicon operatingState"
              @click="handleStatus"><i />Status</span>
        <span class="navicon mesCenter">
          <router-link to="/inform">
          <i />
          <el-badge class="item"

          :class="$store.state.app.statusNumber >= 10 ? 'badgeW' : ''"
                    :value="$store.state.app.statusNumber===0?null:($store.state.app.statusNumber > 99 ? '99+' : $store.state.app.statusNumber)"
                    >Notification</el-badge>
          </router-link>
        </span>
      </div>
      <div class="avatar-wrapper">
        <div :class="shopShow?'userInfo':''"
             @mouseenter="toggleShow"
             @mouseleave="mouseLeaves">
          <el-button type="primary"
                     :class="shopShow?'active':''">
            {{ name }}<i class="el-icon-arrow-down" />
          </el-button>
          <div v-if="shopShow"
               class="userList">
            <p class="amendPwdIcon"
               @click="handlePwd">
              Password<i />
            </p>
            <p class="outLogin"
               @click="logout">
              Logout<i />
            </p>
          </div>
        </div>
      </div>
    </div>
    <!-- Business Status Dialog -->
    <el-dialog title="Business Status"
               :visible.sync="dialogVisible"
               width="25%"
               :show-close="false">
      <el-radio-group v-model="setStatus">
        <el-radio :label="1">
          Open
          <span>The current restaurant is in the open state, automatically receiving any orders, and can click to enter the shop closing state.</span>
        </el-radio>
        <el-radio :label="0">
          Closed
          <span>The current restaurant is in the closed state, only accepting reservations within business hours, and can click to manually restore the open state.</span>
        </el-radio>
      </el-radio-group>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary"
                   @click="handleSave">Confirm</el-button>
      </span>
    </el-dialog>
    <!-- end -->
    <!-- Modify Password -->
    <Password :dialog-form-visible="dialogFormVisible"
              @handleclose="handlePwdClose" />
    <!-- end -->
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import { AppModule } from '@/store/modules/app'
import { UserModule } from '@/store/modules/user'
import Breadcrumb from '@/components/Breadcrumb/index.vue'
import Hamburger from '@/components/Hamburger/index.vue'
import { getStatus, setStatus } from '@/api/users'
import Cookies from 'js-cookie'
import { debounce, throttle } from '@/utils/common'
import { setNewData, getNewData } from '@/utils/cookies'

// Interface
import { getCountUnread } from '@/api/inform'
// Modify Password Dialog
import Password from '../components/password.vue'

@Component({
  name: 'Navbar',
  components: {
    Breadcrumb,
    Hamburger,
    Password,
  },
})
export default class extends Vue {
  private storeId = this.getStoreId
  private restKey: number = 0
  private websocket = null
  private newOrder = ''
  private message = ''
  private audioIsPlaying = false
  private audioPaused = false
  private statusValue = true
  private audioUrl: './../../../assets/preview.mp3'
  private shopShow = false
  private dialogVisible = false
  private status = 1
  private setStatus = 1
  private dialogFormVisible = false
  private ountUnread = 0
  // get ountUnread() {
  //   return Number(getNewData())
  // }
  get sidebar() {
    return AppModule.sidebar
  }

  get device() {
    return AppModule.device.toString()
  }

  getuserInfo() {
    return UserModule.userInfo
  }

  get name() {
    return (UserModule.userInfo as any).name
      ? (UserModule.userInfo as any).name
      : JSON.parse(Cookies.get('user_info') as any).name
  }

  get getStoreId() {
    let storeId = ''
    if (UserModule.storeId) {
      storeId = UserModule.storeId
    } else if ((UserModule.userInfo as any).stores != null) {
      storeId = (UserModule.userInfo as any).stores[0].storeId
    }
    return storeId
  }
  mounted() {
    document.addEventListener('click', this.handleClose)
    //console.log(this.$store.state.app.statusNumber)
    // const msg = {
    //   data: {
    //     type: 2,
    //     content: 'Order 1653904906519 customer reminder, placed 23 minutes ago, still not accepted.',
    //     details: '434'
    //   }
    // }
    this.getStatus()
  }
  created() {
    this.webSocket()
  }
  onload() {
  }
  destroyed() {
    this.websocket.close() //Leave the route and disconnect the websocket connection
  }

  // Add new order prompt dialog
  webSocket() {
    const that = this as any
    let clientId = Math.random().toString(36).substr(2)
    let socketUrl = process.env.VUE_APP_SOCKET_URL + clientId
    console.log(socketUrl, 'socketUrl')
    if (typeof WebSocket == 'undefined') {
      that.$notify({
        title: 'Alert',
        message: 'The current browser cannot receive real-time alarm information, please use Google Chrome!',
        type: 'warning',
        duration: 0,
      })
    } else {
      this.websocket = new WebSocket(socketUrl)
      // Listen for socket opening
      this.websocket.onopen = function () {
        console.log('Browser WebSocket opened')
      }
      // Listen for socket message reception
      this.websocket.onmessage = function (msg) {
        // Convert to json object
        that.$refs.audioVo.currentTime = 0
        that.$refs.audioVo2.currentTime = 0

        console.log(msg, JSON.parse(msg.data), 'msg')
        // const h = this.$createElement
        const jsonMsg = JSON.parse(msg.data)
        if (jsonMsg.type === 1) {
          that.$refs.audioVo.play()
        } else if (jsonMsg.type === 2) {
          that.$refs.audioVo2.play()
        }
        that.$notify({
          title: jsonMsg.type === 1 ? 'Pending' : 'Reminder',
          duration: 0,
          dangerouslyUseHTMLString: true,
          onClick: () => {
            that.$router
              .push(`/order?orderId=${jsonMsg.orderId}`)
              .catch((err) => {
                console.log(err)
              })
            setTimeout(() => {
              location.reload()
            }, 100)
          },
          // Here you can also add the return information to the message to display
          message: `${
            jsonMsg.type === 1
              ? `<span>You have 1 <span style=color:#419EFF>order pending</span>,${jsonMsg.content}, please accept it in time</span>`
              : `${jsonMsg.content}<span style='color:#419EFF;cursor: pointer'>Handle</span>`
          }`,
        })
      }
      // Listen for socket error
      this.websocket.onerror = function () {
        that.$notify({
          title: 'Error',
          message: 'Server error, cannot receive real-time alarm information',
          type: 'error',
          duration: 0,
        })
      }
      // Listen for socket closing
      this.websocket.onclose = function () {
        console.log('WebSocket closed')
      }
    }
  }

  private toggleSideBar() {
    AppModule.ToggleSideBar(false)
  }
  // Logout
  private async logout() {
    this.$store.dispatch('LogOut').then(() => {
      // location.href = '/'
      this.$router.replace({ path: '/login' })
    })
    // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
  }
  // Get unread messages
  async getCountUnread() {
    const { data } = await getCountUnread()
    if (data.code === 1) {
      // this.ountUnread = data.data
      AppModule.StatusNumber(data.data)
      // setNewData(data.data)
      // this.$message.success('Operation successful!')
    } else {
      this.$message.error(data.msg)
    }
  }
  // Business Status
  async getStatus() {
    const { data } = await getStatus()
    this.status = data.data
    this.setStatus = this.status
  }
  // Dropdown menu display
  toggleShow() {
    this.shopShow = true
  }
  // Dropdown menu hidden
  mouseLeaves() {
    this.shopShow = false
  }
  // Trigger blank area dropdown menu close
  handleClose() {
    // clearTimeout(this.leave)
    // this.shopShow = false
  }
  // Set business status
  handleStatus() {
    this.dialogVisible = true
  }
  // Business status settings
  async handleSave() {
    const { data } = await setStatus(this.setStatus)
    if (data.code === 1) {
      this.dialogVisible = false
      this.getStatus()
    }
  }
  // Modify password
  handlePwd() {
    this.dialogFormVisible = true
  }
  // Close password edit dialog
  handlePwdClose() {
    this.dialogFormVisible = false
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 60px;
  // overflow: hidden;
  position: relative;
  background: #ffc100;

  // box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  .statusBox {
    float: left;
    height: 100%;
    align-items: center;
    display: flex;
  }
  .hamburger-container {
    // line-height: 54px;

    padding: 0 12px 0 20px;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }
  .right-menu {
    float: right;

    margin-right: 20px;

    color: #333333;
    font-size: 14px;

    span {
      padding: 0 10px;
      display: inline-block;
      cursor: pointer;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      max-width: 150px;
      &:hover {
        background: rgba(255, 255, 255, 0.52);
      }
    }
    .amendPwdIcon {
      i {
        width: 18px;
        height: 18px;
        background: url(./../../../assets/icons/btn_gaimi@2x.png) no-repeat;
        background-size: contain;
        margin-top: 8px;
      }
    }
    .outLogin {
      i {
        width: 18px;
        height: 18px;
        background: url(./../../../assets/icons/btn_close@2x.png) no-repeat 100%
          100%;
        background-size: contain;
        margin-top: 8px;
      }
    }
    .outLogin {
      cursor: pointer;
    }

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    // .avatar-container {
    // margin-right: 30px;

    // }
  }
  .rightStatus {
    height: 100%;
    line-height: 60px;
    display: flex;
    align-items: center;
    float: left;
    flex-wrap: nowrap;
    gap: 5px;
  }
  .avatar-wrapper {
    margin-top: 14px;
    margin-left: 18px;
    position: relative;
    // vertical-align: middle;
    float: right;
    min-width: 120px;
    max-width: 160px;
    text-align: left;
    .user-avatar {
      cursor: pointer;
      width: 40px;
      height: 40px;
      border-radius: 10px;
    }

    .el-icon-caret-bottom {
      cursor: pointer;
      position: absolute;
      right: -20px;
      top: 25px;
      font-size: 12px;
    }

    .el-button--primary {
      // height: 32px;
      background: rgba(255, 255, 255, 0.52);
      border-radius: 4px;
      padding-top: 0px;
      padding-bottom: 0px;
      position: relative;
      // top: -15px;
      min-width: 120px;
      max-width: 160px;
      // padding: 11px 12px 10px;
      padding-left: 12px;
      padding-right: 32px;
      text-align: left;
      border: 0 none;
      height: 32px;
      line-height: 32px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      &.active {
        background: rgba(250, 250, 250, 0);
        border: 0 none;
        .el-icon-arrow-down {
          transform: rotate(-180deg);
        }
      }
    }
  }
  .businessBtn {
    height: 22px;
    line-height: 20px;
    background: #fd3333;
    border: 1px solid #ffffff;
    border-radius: 4px;
    display: inline-block;
    padding: 0 6px;
    color: #fff;
  }
  .closing {
    background: #6a6a6a;
  }
  .navicon {
    i {
      display: inline-block;
      width: 18px;
      height: 18px;
      vertical-align: sub;
      margin: 0 4px 0 0;
    }
  }
  .operatingState {
    i {
      background: url('./../../../assets/icons/time.png') no-repeat;
      background-size: contain;
    }
  }
  .mesCenter {
    i {
      background: url('./../../../assets/icons/msg.png') no-repeat;
      background-size: contain;
    }
  }
  // .el-badge__content.is-fixed {
  //   top: 20px;
  //   right: 6px;
  // }
}
</style>
<style lang="scss">
.el-notification {
  // background: rgba(255, 255, 255, 0.71);
  width: 419px !important;
  .el-notification__title {
    margin-bottom: 14px;
    color: #333;
    .el-notification__content {
      color: #333;
    }
  }
}
.navbar {
  .el-dialog {
    min-width: auto !important;
  }
  .el-dialog__header {
    height: 61px;
    line-height: 60px;
    background: #fbfbfa;
    padding: 0 30px;
    font-size: 16px;
    color: #333;
    border: 0 none;
  }
  .el-dialog__body {
    padding: 10px 30px 30px;
    .el-radio,
    .el-radio__input {
      white-space: normal;
    }
    .el-radio__label {
      padding-left: 5px;
      color: #333;
      font-weight: 700;
      span {
        display: block;
        line-height: 20px;
        padding-top: 12px;
        color: #666;
        font-weight: normal;
      }
    }
    .el-radio__input.is-checked .el-radio__inner {
      &::after {
        background: #333;
      }
    }
    .el-radio-group {
      & > .is-checked {
        border: 1px solid #ffc200;
      }
    }
    .el-radio {
      width: 100%;
      background: #fbfbfa;
      border: 1px solid #e5e4e4;
      border-radius: 4px;
      padding: 14px 22px;
      margin-top: 20px;
    }
    .el-radio__input.is-checked + .el-radio__label {
      span {
      }
    }
  }
  .el-badge__content.is-fixed {
    top: 24px;
    right: 2px;
    width: 18px;
    height: 18px;
    font-size: 10px;
    line-height: 16px;
    font-size: 10px;
    border-radius: 50%;
    padding: 0;
  }
  .badgeW {
    .el-badge__content.is-fixed {
      width: 30px;
      border-radius: 20px;
    }
  }
}
.el-icon-arrow-down {
  background: url('./../../../assets/icons/up.png') no-repeat 50% 50%;
  background-size: contain;
  width: 8px;
  height: 8px;
  transform: rotate(0eg);
  margin-left: 16px;
  position: absolute;
  right: 16px;
  top: 12px;
  &:before {
    content: '';
  }
}

.userInfo {
  background: #fff;
  position: absolute;
  top: 0px;
  left: 0;
  z-index: 99;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.14);
  width: 100%;
  border-radius: 4px;
  line-height: 32px;
  padding: 0 0 5px;
  height: 105px;
  // .active {
  //   top: 0;
  //   left: 0;
  // }
  .userList {
    width: 95%;
    // // margin-top: -5px;
    // position: absolute;
    // top: 35px;
    padding-left: 5px;
  }
  p {
    cursor: pointer;
    height: 32px;
    line-height: 32px;
    padding: 0 5px 0 7px;
    i {
      margin-left: 10px;

      vertical-align: middle;
      margin-top: 4px;
      float: right;
    }
    &:hover {
      background: #f6f1e1;
    }
  }
}
.msgTip {
  color: #419eff;
  padding: 0 5px;
}
// .el-dropdown{
//   .el-button--primary{
//     height: 32px;
//     background: rgba(255,255,255,0.52);
//     border-radius: 4px;
//     padding-top: 0px;
//     padding-bottom: 0px;
//   }
//   margin-top: 2px;
// }
// .el-popper{
//   top: 45px !important;
//   padding-top: 50px !important;
//   border-radius: 0 0 4px 4px;
// }
// .el-popper[x-placement^=bottom] .popper__arrow::after,.popper__arrow{
//   display: none !important;
// }

// Responsive styles for smaller screens
@media (max-width: 768px) {
  .navbar {
    .right-menu {
      span {
        padding: 0 5px;
        max-width: 100px;
        font-size: 12px;
      }
    }
    .avatar-wrapper {
      min-width: 100px;
      max-width: 120px;
      .el-button--primary {
        min-width: 100px;
        max-width: 120px;
        font-size: 12px;
        padding-left: 8px;
        padding-right: 25px;
      }
    }
  }
}
</style>

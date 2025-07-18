<template>
  <div class="dashboard-container">
    <div class="container">
      <div class="tableBar">
        <label style="margin-right: 10px">Setmeal name:</label>
        <el-input v-model="input"
                  placeholder="Please enter the setmeal name"
                  style="width: 14%"
                  clearable
                  @clear="init"
                  @keyup.enter.native="initFun" />

        <label style="margin-right: 10px; margin-left: 20px">Setmeal category:</label>
        <el-select v-model="categoryId"
                   style="width: 14%"
                   placeholder="Please select"
                   clearable
                   @clear="init">
          <el-option v-for="item in dishCategoryList"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value" />
        </el-select>

        <label style="margin-right: 10px; margin-left: 20px">Sale status:</label>
        <el-select v-model="dishStatus"
                   style="width: 14%"
                   placeholder="Please select"
                   clearable
                   @clear="init">
          <el-option v-for="item in saleStatus"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value" />
        </el-select>
        <el-button class="normal-btn continue"
                   @click="init(true)">
          Search
        </el-button>
        <div class="tableLab">
          <span class="delBut non"
                @click="deleteHandle('批量')">Batch delete</span>
          <!-- <span class="blueBug non" @click="statusHandle('1')">Batch start sale</span>
          <span
            style="border: none"
            class="delBut non"
            @click="statusHandle('0')"
            >Batch stop sale</span
          > -->
          <el-button type="primary"
                     style="margin-left: 15px"
                     @click="addSetMeal('add')">
            + New setmeal
          </el-button>
        </div>
      </div>
      <el-table v-if="tableData.length"
                :data="tableData"
                stripe
                class="tableBox"
                @selection-change="handleSelectionChange">
        <el-table-column type="selection"
                         width="25" />
        <el-table-column prop="name"
                         label="Setmeal name" />
        <el-table-column prop="image"
                         label="Image">
          <template slot-scope="{ row }">
            <el-image style="width: 80px; height: 40px; border: none; cursor: pointer"
                      :src="row.image">
              <div slot="error"
                   class="image-slot">
                <img src="./../../assets/noImg.png"
                     style="width: auto; height: 40px; border: none">
              </div>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName"
                         label="Setmeal category" />
        <el-table-column prop="price"
                         label="Setmeal price">
          <template slot-scope="scope">
            <span>￥{{ ((scope.row.price ).toFixed(2) * 100) / 100 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="Sale status">
          <template slot-scope="scope">
            <div class="tableColumn-status"
                 :class="{ 'stop-use': String(scope.row.status) === '0' }">
              {{ String(scope.row.status) === '0' ? 'Out of stock' : 'In stock' }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime"
                         label="Last operation time">
          <!-- <template slot-scope="scope">
            {{ moment(scope.row.lastUpdateTime).format('YYYY-MM-DD h:m:s') }}
          </template> -->
        </el-table-column>
        <el-table-column label="Operation"
                         width="250"
                         align="center">
          <template slot-scope="scope">
            <el-button type="text"
                       size="small"
                       class="blueBug"
                       @click="addSetMeal(scope.row)">
              Modify
            </el-button>
            <el-button type="text"
                       size="small"
                       class="delBut"
                       @click="deleteHandle('单删', scope.row.id)">
              Delete
            </el-button>
            <el-button type="text"
                       size="small"
                       class="blueBug non"
                       :class="{
                         blueBug: scope.row.status == '0',
                         delBut: scope.row.status != '0'
                       }"
                       @click="statusHandle(scope.row)">
              {{ scope.row.status == '0' ? 'In stock' : 'Out of stock' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <Empty v-else
             :is-search="isSearch" />
      <el-pagination v-if="counts > 10"
                     class="pageList"
                     :page-sizes="[10, 20, 30, 40]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="counts"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange" />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import {
  getSetmealPage,
  editSetmeal,
  deleteSetmeal,
  setmealStatusByStatus,
  dishCategoryList
} from '@/api/setMeal'
import InputAutoComplete from '@/components/InputAutoComplete/index.vue'
import Empty from '@/components/Empty/index.vue'
import { baseUrl } from '@/config.json'

@Component({
  name: 'package',
  components: {
    HeadLable,
    InputAutoComplete,
    Empty
  }
})
export default class extends Vue {
  private input: any = ''
  private counts: number = 0
  private page: number = 1
  private pageSize: number = 10
  private checkList: any[] = []
  private tableData: [] = []
  private dishCategoryList = []
  private categoryId = ''
  private dishStatus = ''
  private isSearch: boolean = false
  private saleStatus: any = [
    {
      value: 0,
      label: 'Out of stock'
    },
    {
      value: 1,
      label: 'In stock'
    }
  ]

  created() {
    this.init()
    this.getDishCategoryList()
  }

  initProp(val) {
    this.input = val
    this.initFun()
  }

  initFun() {
    this.page = 1
    this.init()
  }

  private async init(isSearch?) {
    this.isSearch = isSearch
    await getSetmealPage({
      page: this.page,
      pageSize: this.pageSize,
      name: this.input || undefined,
      categoryId: this.categoryId || undefined,
      status: this.dishStatus
    })
      .then(res => {
        if (res && res.data && res.data.code === 1) {
          this.tableData = res.data.data.records
          this.counts = Number(res.data.data.total)
        } else {
          this.$message.error(res.data.msg)
        }
      })
      .catch(err => {
        this.$message.error('Request failed：' + err.message)
      })
  }

  // Add changes
  private addSetMeal(st: any) {
    if (st === 'add') {
      this.$router.push({ path: '/setmeal/add' })
    } else {
      this.$router.push({ path: '/setmeal/add', query: { id: st.id } })
    }
  }

  // Delete
  private deleteHandle(type: string, id: any) {
    if (type === 'batch' && id === null) {
      if (this.checkList.length === 0) {
        return this.$message.error('Please select the object to delete')
      }
    }
    this.$confirm('Are you sure you want to delete this setmeal?', 'Delete', {
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      type: 'warning'
    }).then(() => {
      deleteSetmeal(type === 'batch' ? this.checkList.join(',') : id)
        .then(res => {
          if (res.data.code === 1) {
            this.$message.success('The setmeal was deleted successfully!')
            this.init()
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch(err => {
          this.$message.error('Request failed：' + err.message)
        })
    })
  }

  // Status changes
  private statusHandle(row: any) {
    let params: any = {}
    if (typeof row === 'string') {
      if (this.checkList.length == 0) {
        this.$message.error('Batch operation, please select the operation dishes first!')
        return false
      }
      params.ids = this.checkList.join(',')
      params.status = row
    } else {
      params.ids = row.id
      params.status = row.status ? '0' : '1'
    }

    this.$confirm('Are you sure you want to change the setmeal status?', 'Change', {
      confirmButtonText: 'Change',
      cancelButtonText: 'Cancel',
      type: 'warning'
    }).then(() => {
      setmealStatusByStatus(params)
        .then(res => {
          if (res.data.code === 1) {
            this.$message.success('The setmeal status has been changed successfully!')
            this.init()
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch(err => {
          this.$message.error('Request failed：' + err.message)
        })
    })
  }

  // Get setmeal category dropdown data
  private getDishCategoryList() {
    dishCategoryList({
      type: 2
    })
      .then(res => {
        if (res && res.data && res.data.code === 1) {
          this.dishCategoryList = (
            res.data &&
            res.data.data &&
            res.data.data
          ).map(item => {
            return { value: item.id, label: item.name }
          })
        }
      })
      .catch(() => {})
  }

  // All
  private handleSelectionChange(val: any) {
    let checkArr: string[] = []
    val.forEach((n: any) => {
      checkArr.push(n.id)
    })
    this.checkList = checkArr
  }

  private handleSizeChange(val: any) {
    this.pageSize = val
    this.init()
  }

  private handleCurrentChange(val: any) {
    this.page = val
    this.init()
  }
}
</script>
<style lang="scss">
.el-table-column--selection .cell {
  padding-left: 10px;
}
</style>
<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;

    .container {
      background: #fff;
      position: relative;
      z-index: 1;
      padding: 30px 28px;
      border-radius: 4px;

      .tableBar {
        margin-bottom: 20px;
        .tableLab {
          float: right;
          span {
            cursor: pointer;
            display: inline-block;
            font-size: 14px;
            padding: 0 20px;
            color: $gray-2;
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
      // Search black button style
      .normal-btn {
        background: #333333;
        color: white;
        margin-left: 20px;
      }
    }
  }
}
</style>

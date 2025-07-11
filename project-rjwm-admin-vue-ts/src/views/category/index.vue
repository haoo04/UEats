<template>
  <div class="dashboard-container">
    <div class="container">
      <div class="tableBar"
           style="display: inline-block; width: 100%">
        <label style="margin-right: 10px">Category Name:</label>
        <el-input v-model="name"
                  placeholder="Please enter category name"
                  style="width: 15%"
                  clearable
                  @clear="init"
                  @keyup.enter.native="init" />

        <label style="margin-right: 5px; margin-left: 20px">Category Type:</label>
        <el-select v-model="categoryType"
                   placeholder="Please select"
                   clearable
                   style="width: 15%"
                   @clear="init">
          <el-option v-for="item in options"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value" />
        </el-select>

        <div style="float: right">
          <el-button type="primary"
                     class="continue"
                     @click="addClass('class')">
            + Add Dish Category
          </el-button>
          <el-button type="primary"
                     style="margin-left:20px"
                     @click="addClass('meal')">
            + Add Set Meal Category
          </el-button>
        </div>

        <el-button class="normal-btn continue"
                   @click="init(true)">
          Search
        </el-button>
      </div>
      <el-table v-if="tableData.length"
                :data="tableData"
                stripe
                class="tableBox">
        <el-table-column prop="name"
                         label="Category Name" />
        <el-table-column prop="type"
                         label="Category Type">
          <template slot-scope="scope">
            <span>{{ scope.row.type == '1' ? 'Dish Category' : 'Set Meal Category' }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="sort"
                         label="Sort Order" />
        <el-table-column label="Status">
          <template slot-scope="scope">
            <div class="tableColumn-status"
                 :class="{ 'stop-use': String(scope.row.status) === '0' }">
              {{ String(scope.row.status) === '0' ? 'Disabled' : 'Enabled' }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime"
                         label="Operation Time" />
        <el-table-column label="Actions"
                         width="200"
                         align="center">
          <template slot-scope="scope">
            <el-button type="text"
                       size="small"
                       class="blueBug"
                       @click="editHandle(scope.row)">
              Edit
            </el-button>
            <el-button type="text"
                       size="small"
                       class="delBut"
                       @click="deleteHandle(scope.row.id)">
              Delete
            </el-button>
            <el-button type="text"
                       size="small"
                       class="non"
                       :class="{
                         blueBug: scope.row.status == '0',
                         delBut: scope.row.status != '0'
                       }"
                       @click="statusHandle(scope.row)">
              {{ scope.row.status == '1' ? 'Disable' : 'Enable' }}
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
    <el-dialog :title="classData.title"
               :visible.sync="classData.dialogVisible"
               width="30%"
               :before-close="handleClose">
      <el-form ref="classData"
               :model="classData"
               class="demo-form-inline"
               :rules="rules"
               label-width="100px">
        <el-form-item label="Category Name:"
                      prop="name">
          <el-input v-model="classData.name"
                    placeholder="Please enter category name"
                    maxlength="20" />
        </el-form-item>
        <el-form-item label="Sort Order:"
                      prop="sort">
          <el-input v-model="classData.sort"
                    placeholder="Please enter sort order" />
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button size="medium"
                   @click="
            ;(classData.dialogVisible = false), $refs.classData.resetFields()
                   ">Cancel</el-button>
        <el-button type="primary"
                   :class="{ continue: actionType === 'add' }"
                   size="medium"
                   @click="submitForm()">Confirm</el-button>
        <el-button v-if="action != 'edit'"
                   type="primary"
                   size="medium"
                   @click="submitForm('go')">
          Save and Continue Adding
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import {
  getCategoryPage,
  deleCategory,
  editCategory,
  addCategory,
  enableOrDisableEmployee
} from '@/api/category'
import Empty from '@/components/Empty/index.vue'

@Component({
  name: 'Category',
  components: {
    HeadLable,
    Empty
  }
})
export default class extends Vue {
  private options: any = [
    {
      value: 1,
      label: 'Dish Category'
    },
    {
      value: 2,
      label: 'Set Meal Category'
    }
  ]
  private actionType: string = ''
  private id = ''
  private status = ''
  private categoryType: number = null
  private name: string = ''
  private action: string = ''
  private counts: number = 0
  private page: number = 1
  private pageSize: number = 10
  private tableData = []
  private type = ''
  private isSearch: boolean = false
  private classData: any = {
    title: 'Add Dish Category',
    dialogVisible: false,
    categoryId: '',
    name: '',
    sort: ''
  }

  get rules() {
    return {
      name: [
        {
          required: true,
          trigger: 'blur',
          validator: (rule: any, value: string, callback: Function) => {
            // const reg = /[\u4e00-\u9fa5]/
            var reg = new RegExp('^[A-Za-z\u4e00-\u9fa5]+$')
            if (!value) {
              callback(new Error(this.classData.title + ' cannot be empty'))
            } else if (value.length < 2) {
              callback(new Error('Category name is invalid, please enter 2-20 characters'))
            } else if (!reg.test(value)) {
              callback(new Error('Category name contains special characters'))
            } else {
              callback()
            }
          }
        }
      ],
      sort: [
        {
          required: true,
          trigger: 'blur',
          validator: (rule: any, value: string, callback: Function) => {
            if (value || String(value) === '0') {
              const reg = /^\d+$/
              if (!reg.test(value)) {
                callback(new Error('Sort order must be numeric'))
              } else if (Number(value) > 99) {
                callback(new Error('Sort order must be between 0-99'))
              } else {
                callback()
              }
            } else {
              callback(new Error('Sort order cannot be empty'))
            }
          }
        }
      ]
    }
  }

  created() {
    this.init()
  }

  // Initialize information
  private async init(isSearch?) {
    this.isSearch = isSearch
    await getCategoryPage({
      page: this.page,
      pageSize: this.pageSize,
      name: this.name ? this.name : undefined,
      type: this.categoryType ? this.categoryType : undefined
    })
      .then(res => {
        if (String(res.data.code) === '1') {
          this.tableData =
            res && res.data && res.data.data && res.data.data.records
          this.counts = Number(res.data.data.total)
        } else {
          this.$message.error(res.data.desc)
        }
      })
      .catch(err => {
        console.log(err, 'err')
        this.$message.error('Request error: ' + err.message)
      })
  }

  // Add
  private addClass(st: any) {
    if (st == 'class') {
      this.classData.title = 'Add Dish Category'
      this.type = '1'
    } else {
      this.classData.title = 'Add Set Meal Category'
      this.type = '2'
    }
    this.action = 'add'
    this.classData.name = ''
    this.classData.sort = ''
    this.classData.dialogVisible = true
    this.actionType = 'add'
  }

  // Edit
  private editHandle(dat: any) {
    this.classData.title = 'Edit Category'
    this.action = 'edit'
    this.classData.name = dat.name
    this.classData.sort = dat.sort
    this.classData.id = dat.id
    this.classData.dialogVisible = true
    this.actionType = 'edit'
  }

  // Close dialog
  private handleClose(st: string) {
    console.log(this.$refs.classData, 'this.$refs.classData')
    this.classData.dialogVisible = false
    // Reset the form item, reset its value to the initial value and remove the verification result
    this.$refs.classData.resetFields()
  }

  //Status modification
  private statusHandle(row: any) {
    this.id = row.id
    this.status = row.status
    this.$confirm('Are you sure you want to change the status of this category?', 'Warning', {
      confirmButtonText: 'Confirm',
      cancelButtonText: 'Cancel',
      type: 'warning',
      customClass: 'customClass'
    }).then(() => {
      enableOrDisableEmployee({ id: this.id, status: !this.status ? 1 : 0 })
        .then(res => {
          if (String(res.status) === '200') {
            this.$message.success('Category status changed successfully!')
            this.init()
          }
        })
        .catch(err => {
          this.$message.error('Request failed: ' + err.message)
        })
    })
  }

  //Delete
  private deleteHandle(id: any) {
    this.$confirm('This action will permanently delete the category. Continue?', 'Confirm Deletion', {
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      type: 'warning'
    }).then(() => {
      deleCategory(id)
        .then(res => {
          if (res.data.code === 1) {
            this.$message.success('Deleted successfully!')
            this.init()
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch(err => {
          this.$message.error('Request failed: ' + err.message)
        })
    })
  }

  $refs!: {
    classData: any
  }

  //Data submission
  submitForm(st: any) {
    if (this.action === 'add') {
      this.$refs.classData.validate((value: boolean) => {
        if (value) {
          addCategory({
            name: this.classData.name,
            type: this.type,
            sort: this.classData.sort
          })
            .then(res => {
              if (res.data.code === 1) {
                this.$message.success('Category added successfully!')
                this.$refs.classData.resetFields()
                if (!st) {
                  this.classData.dialogVisible = false
                }
                this.init()
              } else {
                this.$message.error(res.data.desc || res.data.msg)
              }
            })
            .catch(err => {
              this.$message.error('Request failed: ' + err.message)
            })
        }
      })
    } else {
      this.$refs.classData.validate((value: boolean) => {
        if (value) {
          editCategory({
            id: this.classData.id,
            name: this.classData.name,
            sort: this.classData.sort
          })
            .then(res => {
              if (res.data.code === 1) {
                this.$message.success('Category modified successfully!')
                this.classData.dialogVisible = false
                this.$refs.classData.resetFields()
                this.init()
              } else {
                this.$message.error(res.data.desc || res.data.msg)
              }
            })
            .catch(err => {
              this.$message.error('Request failed: ' + err.message)
            })
        }
      })
    }
  }

  //Pagination
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
        display: flex;
        margin-bottom: 20px;
        justify-content: space-between;
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
      //查询黑色按钮样式
      .normal-btn {
        background: #333333;
        color: white;
        margin-left: 20px;
      }
    }
  }
}
</style>
<style lang='scss'>
// .customClass {
//   .el-button--primary {
//     background-color: #ffc200 !important ;
//   }
// }
</style>

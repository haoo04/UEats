<template>
  <div class="addBrand-container">
    <HeadLable :title="title"
               :goback="true" />
    <div class="container">
      <el-form ref="ruleForm"
               :model="ruleForm"
               :rules="rules"
               :inline="false"
               label-width="180px"
               class="demo-ruleForm">
        <el-form-item label="Account:"
                      prop="username">
          <el-input v-model="ruleForm.username"
                    placeholder="Please enter account"
                    maxlength="20" />
        </el-form-item>
        <el-form-item label="Employee Name:"
                      prop="name">
          <el-input v-model="ruleForm.name"
                    placeholder="Please enter employee name"
                    maxlength="12" />
        </el-form-item>
        <el-form-item label="Phone:"
                      prop="phone">
          <el-input v-model="ruleForm.phone"
                    placeholder="Please enter phone number"
                    maxlength="11" />
        </el-form-item>
        <el-form-item label="Gender:"
                      prop="sex">
          <el-radio-group v-model="ruleForm.sex">
            <el-radio label="Male" />
            <el-radio label="Female" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="ID Number:"
                      prop="idNumber"
                      class="idNumber">
          <el-input v-model="ruleForm.idNumber"
                    placeholder="Please enter ID number"
                    maxlength="20" />
        </el-form-item>
        <div class="subBox address">
          <el-button @click="() => $router.push('/employee')">
            Cancel
          </el-button>
          <el-button type="primary"
                     :class="{ continue: actionType === 'add' }"
                     @click="submitForm('ruleForm', false)">
            Save
          </el-button>
          <el-button v-if="actionType == 'add'"
                     type="primary"
                     @click="submitForm('ruleForm', true)">
            Save and Continue Adding
          </el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import { queryEmployeeById, addEmployee, editEmployee } from '@/api/employee'

@Component({
  name: 'addShop',
  components: {
    HeadLable
  }
})
export default class extends Vue {
  private title = 'Add Employee'
  private actionType = ''
  private ruleForm = {
    name: '',
    phone: '',
    sex: 'Male',
    idNumber: '',
    username: ''
  }

  private isCellPhone(val: any) {
    if (!/^01\d{8,9}$/.test(val)) {
      return false
    } else {
      return true
    }
  }

  private checkphone(rule: any, value: any, callback: any) {
    if (value == '') {
      callback(new Error('Please enter phone number'))
    } else if (!this.isCellPhone(value)) {
      callback(new Error('Please enter a valid phone number!'))
    } else {
      callback()
    }
  }

  private validID(rule: any, value: any, callback: any) {
    // ID card number is 12,15 or 18 digits.
    let reg = /(^\d{12}$)|(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
    if (value == '') {
      callback(new Error('Please enter ID number'))
    } else if (reg.test(value)) {
      callback()
    } else {
      callback(new Error('Invalid ID number'))
    }
  }

  get rules() {
    return {
      name: [
        {
          required: true,
          validator: (rule: any, value: string, callback: Function) => {
            if (!value) {
              callback(new Error('Please enter employee name'))
            } else {
              callback()
            }
          },
          trigger: 'blur'
        }
      ],
      username: [
        {
          required: true,
          validator: (rule: any, value: string, callback: Function) => {
            if (!value) {
              callback(new Error('Please enter account'))
            } else {
              const reg = /^([a-z]|[0-9]){3,20}$/
              if (!reg.test(value)) {
                callback(new Error('Invalid account, please enter 3-20 characters'))
              } else {
                callback()
              }
            }
          },
          trigger: 'blur'
        }
      ],
      phone: [{ required: true, validator: this.checkphone, trigger: 'blur' }],
      idNumber: [{ required: true, validator: this.validID, trigger: 'blur' }]
    }
  }

  created() {
    this.actionType = this.$route.query.id ? 'edit' : 'add'
    if (this.$route.query.id) {
      this.title = 'Edit Employee Information'
      this.init()
    }
  }

  private async init() {
    const id = this.$route.query.id
    queryEmployeeById(id).then((res: any) => {
      if (res.data.code === 1) {
        this.ruleForm = res.data.data
        this.ruleForm.sex = res.data.data.sex === '0' ? 'Female' : 'Male'
      } else {
        this.$message.error(res.data.msg)
      }
    })
  }

  private submitForm(formName: any, st: any) {
    ;(this.$refs[formName] as any).validate((valid: any) => {
      if (valid) {
        if (this.actionType === 'add') {
          const params = {
            ...this.ruleForm,
            sex: this.ruleForm.sex === 'Female' ? '0' : '1'
          }
          addEmployee(params)
            .then((res: any) => {
              if (res.data.code === 1) {
                this.$message.success('Employee added successfully!')
                if (!st) {
                  this.$router.push({ path: '/employee' })
                } else {
                  this.ruleForm = {
                    username: '',
                    name: '',
                    phone: '',
                    sex: 'Male',
                    idNumber: ''
                  }
                }
              } else {
                this.$message.error(res.data.msg)
              }
            })
            .catch(() => {
            })
        } else {
          const params = {
            ...this.ruleForm,
            sex: this.ruleForm.sex === 'Female' ? '0' : '1'
          }
          editEmployee(params)
            .then((res: any) => {
              if (res.data.code === 1) {
                this.$message.success('Employee information modified successfully!')
                this.$router.push({ path: '/employee' })
              } else {
                this.$message.error(res.data.msg)
              }
            })
            .catch(() => {
            })
        }
      } else {
        return false
      }
    })
  }
}
</script>

<style lang="scss" scoped>
.addBrand {
  &-container {
    margin: 30px;
    margin-top: 0px;
    .HeadLable {
      background-color: transparent;
      margin-bottom: 0px;
      padding-left: 0px;
    }
    .container {
      position: relative;
      z-index: 1;
      background: #fff;
      padding: 30px;
      border-radius: 4px;
      // min-height: 500px;
      .subBox {
        padding-top: 30px;
        text-align: center;
        border-top: solid 1px $gray-5;
      }
    }
    .idNumber {
      margin-bottom: 39px;
    }

    .el-form-item {
      margin-bottom: 29px;
    }
    .el-input {
      width: 293px;
    }
  }
}
</style>

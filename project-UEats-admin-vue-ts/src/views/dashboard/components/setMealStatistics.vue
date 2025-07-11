<template>
  <div class="container">
    <h2 class="homeTitle">
      Set Meal Overview<span
        ><router-link to="setmeal">Set Meal Management</router-link></span
      >
    </h2>
    <div class="orderviewBox">
      <ul>
        <li>
          <span class="status"><i class="iconfont icon-open"></i>On Sale</span>
          <span class="num">{{ setMealData.sold }}</span>
        </li>
        <li>
          <span class="status"><i class="iconfont icon-stop"></i>Discontinued</span>
          <span class="num">{{ setMealData.discontinued }}</span>
        </li>
        <li class="add">
          <router-link to="setmeal/add">
            <i></i>
            <p>Add Set Meal</p>
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator'
@Component({
  name: 'SetMeal',
})
export default class extends Vue {
  @Prop() private setMealData!: any
}
</script>

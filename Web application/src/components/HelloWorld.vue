<template>
  <div class="hello">
    <client-table/>
    <employee-table/>
    <contract-table/>
  </div>
</template>

<script>
import ClientTable from './ClientTable.vue'
import EmployeeTable from "./EmployeeTable.vue";
import ContractTable from "./ContractTable.vue";
export default {
  components: {ContractTable, EmployeeTable, ClientTable },
  name: 'HelloWorld',
  data() {
    return {

    }
  }
}
</script>

<style scoped>

</style>

<template>
  <div>
    <v-row>
      <v-col>
        <h1> Employees table</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-btn @click="addNewRowStart">Add new Employee</v-btn>
      </v-col>
    </v-row>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th
              class="text-center"
              v-for="item in table_header"
              v-bind:key="item"
            >
              {{ item }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in table_data" v-bind:key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.fullName }}</td>
            <td>{{ item.post }}</td>
            <td>{{ item.experience }}</td>
            <td>{{ item.phone }}</td>
            <td>{{ item.email }}</td>
            <td>
              <v-btn icon @click="editRowStart(item)">
                <v-icon>mdi-pen</v-icon>
              </v-btn>
              <v-btn icon @click="deleteRow(item.id)">
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    
    <dialog-example
      :dialog="showDialog"
      :item="newItem"
      :title="title"
      @close="closeDialog"
      @next="nextFunction"
    />
  </div>
</template>

<script>
// Change this import in respect to the technology currently using in the backend 
import { eeGetAll, eeDeleteItem, eeAddNewItem, eeUpdateItem } from "../endpoints/employee_table_endpoints";
// import { springGetAll, springDeleteItem, springAddNewItem, springUpdateItem } from "../endpoints/employee_table_endpoints";

import DialogExample from "./DialogExample.vue";

export default {
  components: { DialogExample },
  name: "EmployeeTable",
  data() {
    return {
      table_header: ["Id", "Full name", "Post", "Experience", "Phone", "Email"],
      table_data: [],
      showDialog: false,
      newItem: {},
      editingItemIndex: -1,
      title: '',
    };
  },
  computed: {},
  mounted() {
    this.getAllRows();
  },
  methods: {
    nextFunction() {
      // Placeholder
    },
    getAllRows() {
      // springGetAll().then((response) => {
      eeGetAll().then((response) => {
          this.table_data = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteRow(itemId) {
      // springDeleteItem(itemId)
      //     .then(() => {
      eeDeleteItem(itemId)
        .then(() => {
          this.getAllRows();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    editRowStart(item) {
      this.title = `Modify existing employee #${item.id}`
      this.editingItemIndex = item.id
      // Defines fields we need to fill in the dialog
      this.newItem = {'fullName': item.fullName, "post": item.post, "experience":item.experience, 'phone': item.phone, 'email': item.email}
      this.nextFunction = this.editRowEnd
      this.showDialog = true;
    },
    editRowEnd(item) {
      // In case of javaEE 
      // addNewItem(item)
      // In case of Srping 
      const body = {'fullName': item.fullName, "post": item.post, "experience":item.experience, 'phone': item.phone, 'email': item.email}
      // params = {'childId': 1}
      const params = {}
      // springUpdateItem(this.editingItemIndex, params, body)
      //     .then((response) => {
      eeUpdateItem(this.editingItemIndex, params, body)
        .then((response) => {
          console.log(response.data);
          this.getAllRows();
        })
        .catch((error) => {
          console.log(error);
        });
      this.editingItemIndex = -1
      this.closeDialog()
    },
    addNewRowStart() {
      this.title = 'Add new client'
      // Defines fields we need to fill in the dialog
      this.newItem = {fullName: '', post: '', experience: '', phone: '', email: ''}
      this.nextFunction = this.addNewRowEnd
      this.showDialog = true;
    },
    addNewRowEnd(item) {
      // In case of javaEE 
      // addNewItem(item)
      // In case of Srping 
      const body = {'fullName': item.fullName, "post": item.post, "experience":item.experience, 'phone': item.phone, 'email': item.email}
      // params = {'childId': 1}
      const params = {}
      // springAddNewItem(params, body)
      //     .then((response) => {
      eeAddNewItem(params, body)
        .then((response) => {
          console.log(response.data);
          this.getAllRows();
        })
        .catch((error) => {
          console.log(error);
        });

      this.closeDialog()
    },
    closeDialog() {
      this.showDialog = false
    },
  },
};
</script>

<style scoped>
</style>

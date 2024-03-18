<template>
 <h1>Get Fees:</h1>
 <get-view></get-view>
 <h1>Set Fees:</h1>
 <set-view></set-view>
 <p style="text-align: left;margin-left:25%">
  Get Fee will give a fee based on the city, vehicle and date.<br>
  Date for which the fee is shown, is the latest date before chosen date<br>
  for which there is an entry in the database.
 </p>
 <p style="text-align: left;margin-left:25%">
  Set Fee will set the fee based on the following inputs:<br>
  * what to set (City/Temperature/Wind Speed/phenomenon)<br>
  * value to set:<br>
  * * City: Tallinn, Tartu, Pärnu<br>
  * * Temperature: -15 (means temperatures below -10), -5 (means temperatures between -10 and 0)<br>
  * * Wind Speed: 15 (means wind speed between 10 and 20), 25 (means wind speeds above 20)<br>
  * * Phenomenon: Snow, Sleet, Rain, Glaze, Hail, Thunder<br>
  * vehicle: Car, Scooter, Bike<br>
  * fee: Double value
 </p>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import GetView from './views/GetView.vue';
import SetView from './views/SetView.vue';

export default defineComponent({
  name: 'App',
  components: {
    GetView, SetView
  }
});
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>

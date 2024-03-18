<template>
  <div class="get">
    <select v-model="station">
      <option>Tallinn</option>
      <option>Tartu</option>
      <option>Pärnu</option>
    </select>
    <select v-model="vehicle">
      <option>Car</option>
      <option>Scooter</option>
      <option>Bike</option>
    </select>
    <input type="datetime-local" v-model="datetime">
    <button @click="getFee()">Get Fee</button>
    {{ fee }}
    {{ errorMessage }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'HomeView',
  data() {
    return {
      station: "Tallinn",
      vehicle: "Car",
      timestamp: 0,
      fee: 0,
      errorMessage: "",
      datetime: ""
    }
  },
  mounted() {
    this.timestamp = new Date().getTime();
    this.datetime = this.getDateTimeFromTimestamp();
  },
  methods: {
    getTimeStamp() {
      const newDate = new Date(this.datetime)
      return newDate.getTime()
    },
    getDateTimeFromTimestamp() {
      var date = new Date(this.timestamp)
      var offset = date.getTimezoneOffset()*60*1000
      return new Date(this.timestamp-offset).toISOString().slice(0,16)
    },
    getFee() {
      if (!["tallinn", "tartu", "pärnu"].includes(this.station.toLowerCase())) {
        this.errorMessage = "Station must be either Tallinn, Tartu or Pärnu"
        this.fee = 0
        return;
      }
      if (!["car", "scooter", "bike"].includes(this.vehicle.toLowerCase())) {
        this.errorMessage = "Station must be either Car, Scooter or Bike"
        this.fee = 0
        return;
      }
      if (typeof(this.timestamp) != "number") {
        this.errorMessage = "Please check if your time input is correct"
        this.fee = 0
        return;
      }
      fetch("http://localhost:3000/fee?station="+this.station+"&vehicle="+this.vehicle+"&timestamp="+this.timestamp)
      .then(response => response.json())
      .then(data => {this.fee = data; this.errorMessage=""})
      .catch(err => {this.errorMessage=err.message; this.fee=0})
    }
  }
});
</script>

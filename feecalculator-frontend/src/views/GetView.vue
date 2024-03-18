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
    <div v-if="!errorMessage">{{ fee }}</div>
    <div v-if="errorMessage">{{ errorMessage }}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'GetView',
  /**
   * Stored variables,
   * station is the city,
   * vehicle is the vehicle,
   * timestamp is the current time's timestamp,
   * fee is the fee gotten from the fetch request
   * errorMessage is a potential error message
   * datetime is a variable that is used for initializing the datetime input field and getting the timestamp.
   */
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
  /**
   * On mount the timestamp and datetime are calculated.
   */
  mounted() {
    this.timestamp = new Date().getTime();
    this.datetime = this.getDateTimeFromTimestamp();
  },
  methods: {
    /**
     * Create a new Date using the datetime value.
     * Then get its timestamp
     */
    getTimeStamp() {
      const newDate = new Date(this.datetime)
      return newDate.getTime()
    },
    /**
     * Create new Date using the timestamp.
     * Get timezone offset in minutes.
     * ISOString doesn't account for timezone offset, so we need to manually remove it.
     * Timestamp is in milliseconds.
     * Slice is used to keep only YYYY-MM-DDThh:mm
     */
    getDateTimeFromTimestamp() {
      var date = new Date(this.timestamp)
      var offset = date.getTimezoneOffset()*60*1000
      return new Date(this.timestamp-offset).toISOString().slice(0,16)
    },
    /**
     * Check if input values are ok.
     * If not, set errorMessage and return.
     * If yes, set errorMessage to "".
     * 
     * Backend timestamp is in seconds, so have to divide by 1000.
     * 
     * If there is an error message, set it, otherwise set it to "".
     */
    getFee() {
      if (!["tallinn", "tartu", "pärnu"].includes(this.station.toLowerCase())) {
        this.errorMessage = "Station must be either Tallinn, Tartu or Pärnu"
        this.fee = 0
        return;
      }
      if (!["car", "scooter", "bike"].includes(this.vehicle.toLowerCase())) {
        this.errorMessage = "Vehicle must be either Car, Scooter or Bike"
        this.fee = 0
        return;
      }
      if (typeof(this.timestamp) != "number") {
        this.errorMessage = "Please check if your time input is correct"
        this.fee = 0
        return;
      }
      this.errorMessage = ""
      this.timestamp = this.getTimeStamp()
      fetch("http://localhost:3000/fee?station="+this.station+"&vehicle="+this.vehicle+"&timestamp="+(this.timestamp/1000))
      .then(response => response.json())
      .then(data => {
        if (data.message) {
          this.errorMessage = data.message
          this.fee = 0
        } else {
          this.fee = data
          this.errorMessage=""
        }
      })
      .catch(err => {this.errorMessage=err.message; this.fee=0})
    }
  }
});
</script>

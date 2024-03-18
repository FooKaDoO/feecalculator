<template>
  <div class="set">
    <div>
      <select v-model="currentWhatToSet">
        <option v-for="what in whatToSet">{{ what }}</option>
      </select>
      <select v-model="currentValToSet">
        <option v-for="val in valToSet[currentWhatToSet]">{{ val }}</option>
      </select>
      <select v-model="currentVehicle">
        <option v-for="veh in vehicle">{{ veh }}</option>
      </select>
      <input type="number" v-model="currentFee">
      <button @click="setFee()">Set Fee</button>
      <div v-if="errorMessage">{{ errorMessage }}</div>
    </div>
  </div>
</template>
<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'SetView',
  /**
   * Stored variables.
   * current... variables are used to make the setFee request.
   * whatToSet, valToSet and vehicle variables are lists which from we can choose which fee to set.
   * errorMessage is used to display potential errors.
   */
  data() {
    return {
      currentWhatToSet: "City",
      currentValToSet: "Tallinn",
      currentVehicle: "Car",
      currentFee: 4.0,

      whatToSet: ["City", "Temperature", "WindSpeed", "Phenomenon"],
      valToSet: {
        "City": ["Tallinn", "Tartu", "Pärnu"],
        "Temperature": ["-15", "-5"],
        "WindSpeed": ["15", "25"],
        "Phenomenon": ["Snow","Sleet","Rain","Glaze","Hail","Thunder"]
      },
      vehicle: ["Car", "Scooter", "Bike"],

      errorMessage: ""
    }
  },
  methods: {
    /**
     * Checks if input is ok.
     * If not, sets errorMessage and returns.
     * If yes, sets errorMessage to "".
     * 
     * Sends a request to the backend, to update the fee based on the current... values.
     * In case of error, sets the errorMessage, otherwise sets errorMessage to "".
     */
    setFee() {
      if (!this.whatToSet.includes(this.currentWhatToSet)) {
        this.errorMessage = "Check if your what to set is correct"
        return;
      }
      if (!this.valToSet[this.currentWhatToSet].includes(this.currentValToSet)) {
        this.errorMessage = "Check if your val to set is correct"
        return;
      }
      if (!this.vehicle.includes(this.currentVehicle)) {
        this.errorMessage = "Vehicle must be either Car, Scooter or Bike"
        return;
      }
      if (typeof(this.currentFee) == "Number") {
        this.errorMessage = "Check if your fee is a number value"
        return;
      }
      this.errorMessage = ""
      fetch("http://localhost:3000/fee?whatToSet="+this.currentWhatToSet+"&valToSet="+this.currentValToSet+"&vehicle="+this.currentVehicle+"&fee="+this.currentFee, {
        method:"POST"
      })
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
      .catch(err => {this.errorMessage=err.message})
    }
  }
});
</script>
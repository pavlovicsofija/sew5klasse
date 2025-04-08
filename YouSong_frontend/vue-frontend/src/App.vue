<template>
  <div id="app">
    <SongComponent />
  </div>
</template>

<script>
import SongComponent from './components/SongComponent.vue';

export default {
  name: 'App',
  components: {
    SongComponent
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

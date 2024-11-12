<template>
  <div id="app">
    <Song />
  </div>
</template>

<script>
import Song from './components/SongComponent.vue'

export default {
  name: 'App',
  components: {
    Song
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

<template>
  <div id="app">
    <SongList />
  </div>
</template>

<script>
import SongList from './components/SongList.vue';

export default {
  name: 'App',
  components: {
    SongList
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

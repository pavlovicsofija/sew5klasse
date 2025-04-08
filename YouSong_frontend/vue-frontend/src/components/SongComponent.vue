<template>
    <div class="song">
      <h3>{{ song.title }}</h3>
      <p>Interpret: {{ song.artist }}</p>
      <p>Genre: {{ song.genre }}</p>
      <p>Länge: {{ song.length }}</p>
    </div>
  </template>
  
  <script>
  export default {
    name: 'SongComponent',
    props: {
      song: {
        type: Object,
        required: true
      }
    }
  }
  </script>
  
  <style scoped>
  /* Styles für den SongComponent */
  </style>
  
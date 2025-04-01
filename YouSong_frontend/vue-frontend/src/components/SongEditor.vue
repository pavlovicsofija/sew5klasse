<template>
    <div>
      <h2>Song Editor</h2>
      <form @submit.prevent="saveSong">
        <div class="form-group">
          <label for="title">Titel</label>
          <input id="title" v-model="song.title" class="form-control" required>
        </div>
        <div class="form-group">
          <label for="artist">Interpret</label>
          <input id="artist" v-model="song.artist" class="form-control" required>
        </div>
        <div class="form-group">
          <label for="genre">Genre</label>
          <input id="genre" v-model="song.genre" class="form-control" required>
        </div>
        <div class="form-group">
          <label for="length">Länge</label>
          <input id="length" v-model="song.length" type="number" step="0.01" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Song speichern</button>
      </form>
    </div>
  </template>
  
  <script>
  import SongService from '../services/SongService';
  
  export default {
    name: "SongEditor",
    data() {
      return {
        song: {
          title: '',
          artist: '',
          genre: '',
          length: 0
        }
      }
    },
    methods: {
      saveSong() {
        SongService.createSong(this.song)
          .then(response => {
            alert('Song erfolgreich gespeichert!');
            // Formular zurücksetzen
            this.song = { title: '', artist: '', genre: '', length: 0 };
          })
          .catch(error => {
            console.error("Fehler beim Speichern des Songs:", error);
          });
      }
    }
  }
  </script>
  
  <style scoped>
  /* Styles für den Editor */
  </style>
  
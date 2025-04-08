<template>
    <div class="container">
      <h1>Songs</h1>
      <table class="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Titel</th>
            <th>Interpret</th>
            <th>Genre</th>
            <th>Länge</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="song in songs" :key="song.id">
            <td>{{ song.id }}</td>
            <td>{{ song.title }}</td>
            <td>{{ song.artist }}</td>
            <td>{{ song.genre }}</td>
            <td>{{ song.length }}</td>
          </tr>
        </tbody>
      </table>
      
      <nav aria-label="Page navigation">
        <ul class="pagination">
          <li :class="['page-item', { disabled: currentPage === 0 }]">
            <button class="page-link" @click="goToFirstPage" :disabled="currentPage === 0">First</button>
          </li>
          <li :class="['page-item', { disabled: currentPage === 0 }]">
            <button class="page-link" @click="goToPreviousPage" :disabled="currentPage === 0">Previous</button>
          </li>
          <li class="page-item disabled">
            <span class="page-link">{{ currentPage + 1 }} / {{ totalPages }}</span>
          </li>
          <li :class="['page-item', { disabled: currentPage >= totalPages - 1 }]">
            <button class="page-link" @click="goToNextPage" :disabled="currentPage >= totalPages - 1">Next</button>
          </li>
          <li :class="['page-item', { disabled: currentPage >= totalPages - 1 }]">
            <button class="page-link" @click="goToLastPage" :disabled="currentPage >= totalPages - 1">Last</button>
          </li>
        </ul>
      </nav>
    </div>
  </template>
  
  <script>
  import SongService from '../services/SongService';
  
  export default {
    name: 'SongList',
    data() {
      return {
        songs: [],
        currentPage: 0,
        totalPages: 0
      }
    },
    created() {
      this.fetchSongs();
    },
    methods: {
      fetchSongs() {
        SongService.getSongs(this.currentPage)
          .then(response => {
            // Der Server liefert ein Page-Objekt mit content, totalPages und number
            this.songs = response.data.content;
            this.totalPages = response.data.totalPages;
            this.currentPage = response.data.number;
          })
          .catch(error => {
            console.error("Error fetching songs:", error);
          });
      },
      goToFirstPage() {
        if (this.currentPage > 0) {
          this.currentPage = 0;
          this.fetchSongs();
        }
      },
      goToPreviousPage() {
        if (this.currentPage > 0) {
          this.currentPage--;
          this.fetchSongs();
        }
      },
      goToNextPage() {
        if (this.currentPage < this.totalPages - 1) {
          this.currentPage++;
          this.fetchSongs();
        }
      },
      goToLastPage() {
        if (this.currentPage < this.totalPages - 1) {
          this.currentPage = this.totalPages - 1;
          this.fetchSongs();
        }
      }
    }
  }
  </script>
  
  <style scoped>
  /* Zusätzliche Styles, falls benötigt */
  </style>
  
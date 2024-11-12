<template>
    <div class="container py-5">
        <h1 class="text-center mb-4 display-4 text-dark">Song List</h1>

        <div class="mb-3">
            <input 
                type="text" 
                v-model="searchQuery" 
                @input="searchSongs" 
                class="form-control" 
                placeholder="Search by title or artist"
            />
        </div>

        <div class="text-end mb-3">
            <button class="btn btn-success" @click="openEditor('create')">Create New Song</button>
        </div>

        <div class="table-responsive bg-dark p-3 rounded-3 shadow-lg" v-if="songs.length > 0">
            <table class="table table-hover table-dark table-striped table-bordered border-secondary align-middle">
                <thead class="text-uppercase text-light">
                    <tr class="bg-gradient">
                        <th scope="col">Song Id</th>
                        <th scope="col">Song Title</th>
                        <th scope="col">Song Artist</th>
                        <th scope="col">Song Genre</th>
                        <th scope="col">Song Length</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="song in songs" :key="song.id">
                        <td>{{ song.id }}</td>
                        <td>{{ song.title }}</td>
                        <td>{{ song.artist }}</td>
                        <td>{{ song.genre }}</td>
                        <td>{{ song.length }}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" @click="openEditor('edit', song)">Edit</button>
                            <button class="btn btn-danger btn-sm" @click="deleteSong(song.id)">Delete</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <p v-if="!songs.length && searchQuery">No songs can be found. Please adjust your search.</p>

        <div v-if="showEditorModal" class="modal show d-block" tabindex="-1" style="background: rgba(0, 0, 0, 0.5);">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">{{ editorMode === 'edit' ? 'Edit Song' : 'Create New Song' }}</h5>
                        <button type="button" class="btn-close" @click="closeEditor"></button>
                    </div>
                    <div class="modal-body">
                        <SongEditor 
                            :mode="editorMode" 
                            :songData="selectedSong" 
                            @close="closeEditor" 
                            @saved="getSongs" 
                        />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SongService from '../services/SongService'
import SongEditor from './SongEditor.vue'

export default {
    name: 'SongList',
    components: {
        SongEditor
    },
    data() {
        return {
            songs: [],
            searchQuery: '',
            showEditorModal: false,
            editorMode: 'create', 
            selectedSong: null
        };
    },
    methods: {
        getSongs() {
            SongService.getSongs().then((response) => {
                this.songs = response.data;
            });
        },
        searchSongs() {
            if (this.searchQuery) {
                SongService.searchSongs(this.searchQuery).then(response => {
                    this.songs = response.data;
                }).catch(() => {
                    this.songs = []; // No matching songs found
                });
            } else {
                this.getSongs(); // If no search query, fetch all songs
            }
        },
        openEditor(mode, song = null) {
            this.editorMode = mode;
            this.selectedSong = song ? { ...song } : { title: '', artist: '', genre: '', length: '' };
            this.showEditorModal = true;
        },
        closeEditor() {
            this.showEditorModal = false;
            this.selectedSong = null;
        },
        deleteSong(songId) {
            if (confirm("Are you sure you want to delete this song?")) {
                SongService.deleteSong(songId).then(() => {
                    this.songs = this.songs.filter(song => song.id !== songId);
                }).catch(error => {
                    console.error("Error deleting song:", error);
                });
            }
        }
    },
    created() {
        this.getSongs();
    }
};
</script>
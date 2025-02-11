<template>
    <div class="container py-5">
        <h1 class="text-center mb-4">Song List</h1>

        <input 
            type="text" 
            v-model="searchQuery" 
            @input="searchSongs" 
            class="form-control mb-3" 
            placeholder="Search by title or artist"
        />

        <button class="btn btn-success mb-3" @click="openEditor('create')">Create New Song</button>

        <div v-if="songs.length > 0" class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Artist</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="song in songs" :key="song.id">
                        <td>{{ song.title }}</td>
                        <td>{{ song.artist }}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" @click="openEditor('edit', song)">Edit</button>
                            <button class="btn btn-info btn-sm" @click="playSong(song.id)">Play</button>
                            <button class="btn btn-danger btn-sm" @click="deleteSong(song.id)">Delete</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <p v-if="!songs.length && searchQuery">No songs found. Try another search.</p>

        <div v-if="selectedSongDataUrl">
            <h3>Now Playing</h3>
            <audio :src="selectedSongDataUrl" controls autoplay></audio>
        </div>

        <div v-if="showEditorModal">
            <SongEditor 
                :mode="editorMode" 
                :songData="selectedSong" 
                @close="closeEditor" 
                @saved="getSongs" 
            />
        </div>
    </div>
</template>

<script>
import SongService from '../services/SongService';
import SongEditor from './SongEditor.vue';

export default {
    components: { SongEditor },
    data() {
        return {
            songs: [],
            searchQuery: '',
            selectedSongDataUrl: '',
            showEditorModal: false,
            editorMode: 'create',
            selectedSong: null
        };
    },
    methods: {
        getSongs() {
            SongService.getSongs()
                .then(response => {
                    this.songs = response.data;
                })
                .catch(error => {
                    console.error("Error fetching songs:", error);
                });
        },
        searchSongs() {
            if (this.searchQuery.trim()) {
                SongService.searchSongs(this.searchQuery)
                    .then(response => {
                        this.songs = response.data;
                    })
                    .catch(() => {
                        this.songs = [];  // Keine Songs gefunden
                    });
            } else {
                this.getSongs();
            }
        },
        playSong(songId) {
            SongService.getSongData(songId) //getSongData(songId) aus SongService
                .then(response => {
                    if (response.data) { //wenn Server erfolgreich die Audiodaten zurückgibt
                        this.selectedSongDataUrl = response.data;
                    } else {
                        alert("No audio data found for this song.");
                    }
                })
                .catch(() => {
                    alert("Error: Unable to load song.");
                });
        },
        deleteSong(songId) {
            if (confirm("Are you sure you want to delete this song?")) {
                SongService.deleteSong(songId)
                    .then(() => {
                        this.getSongs();
                    })
                    .catch(error => {
                        console.error("Error deleting song:", error);
                    });
            }
        },
        openEditor(mode, song = null) {
            this.editorMode = mode;
            this.selectedSong = song ? { ...song } : { title: '', artist: '', genre: '', length: '', dataUrl: '' };
            this.showEditorModal = true;
        },
        closeEditor() {
            this.showEditorModal = false;
            this.selectedSong = null;
        }
    },
    created() {
        this.getSongs();
    }
};
</script>

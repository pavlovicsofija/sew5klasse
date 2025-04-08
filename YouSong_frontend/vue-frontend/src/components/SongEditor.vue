<template>
    <div class="container">
        <h2>{{ mode === 'edit' ? 'Edit Song' : 'Create New Song' }}</h2>
        
        <form @submit.prevent="submitForm">
            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" v-model="song.title" id="title" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="artist" class="form-label">Artist</label>
                <input type="text" v-model="song.artist" id="artist" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="genre" class="form-label">Genre</label>
                <input type="text" v-model="song.genre" id="genre" class="form-control">
            </div>
            <div class="mb-3">
                <label for="length" class="form-label">Length</label>
                <input type="text" v-model="song.length" id="length" class="form-control">
            </div>
        <!-- audio upload -->
            <div class="mb-3">
                <label for="file" class="form-label">Upload Song</label>
                <input type="file" @change="handleFileUpload" class="form-control" accept="audio/*">
            </div>

            <button type="submit" class="btn btn-primary">
                {{ mode === 'edit' ? 'Save Changes' : 'Add Song' }}
            </button>
        </form>

        <!-- Audio-Preview-->
        <div v-if="song.dataUrl" class="mt-3">
            <p>Audio Preview:</p>
            <audio :src="song.dataUrl" controls></audio>
        </div>
    </div>
</template>

<script>
import SongService from '../services/SongService';

export default {
    props: ['mode', 'songData'],
    data() {
        return {
            song: this.songData || { title: '', artist: '', genre: '', length: '', dataUrl: '' }
        };
    },
    methods: {
        handleFileUpload(event) {
            const file = event.target.files[0];
            const reader = new FileReader();
            reader.onload = () => {
                this.song.dataUrl = reader.result;  // speichert base64-url von song
            };
            reader.readAsDataURL(file);
        },
        submitForm() {
            if (this.mode === 'create') {
                SongService.createSong(this.song).then(() => {
                    this.$emit('saved');
                    this.$emit('close');
                });
            } else {
                SongService.updateSong(this.song.id, this.song).then(() => {
                    this.$emit('saved');
                    this.$emit('close');
                });
            }
        }
    }
};
</script>

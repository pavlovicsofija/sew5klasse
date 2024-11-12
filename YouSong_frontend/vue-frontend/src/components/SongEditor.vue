<template>
    <div class="container">
        <h2>{{ mode === 'edit' ? 'Edit Song' : 'Create New Song' }}</h2>
        
        <form @submit.prevent="submitForm">
            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" v-model="song.title" id="title" class="form-control">
            </div>
            <div class="mb-3">
                <label for="artist" class="form-label">Artist</label>
                <input type="text" v-model="song.artist" id="artist" class="form-control">
            </div>
            <div class="mb-3">
                <label for="genre" class="form-label">Genre</label>
                <input type="text" v-model="song.genre" id="genre" class="form-control">
            </div>
            <div class="mb-3">
                <label for="length" class="form-label">Length</label>
                <input type="text" v-model="song.length" id="length" class="form-control">
            </div>

            <button type="submit" class="btn btn-primary">{{ mode === 'edit' ? 'Save Changes' : 'Add Song' }}</button>
        </form>
    </div>
</template>

<script>
import SongService from '../services/SongService';

export default {
    props: ['mode', 'songData'],
    data() {
        return {
            song: this.songData || { title: '', artist: '', genre: '', length: '' }
        };
    },
    methods: {
        submitForm() {
            if (this.mode === 'create') {
                SongService.createSong(this.song).then(() => {
                    //alert("Song created successfully!");
                    this.$emit('saved');
                    this.$emit('close');
                }).catch(error => {
                    console.error("Error creating song:", error);
                });
            } else {
                SongService.updateSong(this.song.id, this.song).then(() => {
                    //alert("Song updated successfully!");
                    this.$emit('saved');
                    this.$emit('close');
                }).catch(error => {
                    console.error("Error updating song:", error);
                });
            }
        }
    }
}
</script>

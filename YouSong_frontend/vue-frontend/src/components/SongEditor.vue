<template>
  <div class="container">
    <h2>{{ mode === 'edit' ? 'Edit Song' : 'Create New Song' }}</h2>

    <!-- Fehlernachrichten -->
    <div v-if="errorMessages && Object.keys(errorMessages).length" class="mb-4">
      <span v-for="(msg, field) in errorMessages" :key="field" class="error-message">
        {{ msg }}
      </span>
    </div>

    <form @submit.prevent="submitForm">
      <div class="mb-3">
        <label for="title" class="form-label">Title</label>
        <input type="text" v-model="song.title" id="title" class="form-control">
        <span v-if="v$.title.$error" class="error-message">Title is required.</span>
      </div>

      <div class="mb-3">
        <label for="artist" class="form-label">Artist</label>
        <input type="text" v-model="song.artist" id="artist" class="form-control">
        <span v-if="v$.artist.$error" class="error-message">Artist is required.</span>
      </div>

      <div class="mb-3">
        <label for="genre" class="form-label">Genre</label>
        <input type="text" v-model="song.genre" id="genre" class="form-control">
        <span v-if="v$.genre.$error" class="error-message">Genre is required.</span>
      </div>

      <div class="mb-3">
        <label for="length" class="form-label">Length (in seconds)</label>
        <input type="text" v-model="song.length" id="length" class="form-control">
        <span v-if="v$.length.$error" class="error-message">
          <span v-if="!v$.length.required">Length is required.</span>
          <span v-else-if="!v$.length.numeric">Length must be a valid number.</span>
          <span v-else-if="!v$.length.minValue">Length must be greater than 0 seconds.</span>
        </span>
      </div>

      <!-- Datei hochladen -->
      <div class="mb-3">
        <label for="file" class="form-label">Upload Song File</label>
        <input type="file" @change="handleFileUpload" class="form-control" accept="audio/*">
        <span v-if="!song.fileData" class="error-message">Please upload a song file.</span>
      </div>

      <button type="submit" class="btn btn-primary">
        {{ mode === 'edit' ? 'Save Changes' : 'Add Song' }}
      </button>
    </form>

    <!-- Audio Vorschau -->
    <div v-if="song.dataUrl" class="mt-3">
      <p>Audio Preview:</p>
      <audio :src="song.dataUrl" controls></audio>
    </div>
  </div>
</template>

<script>
import { reactive, ref, watch } from 'vue';
import useVuelidate from '@vuelidate/core';
import { required, numeric, minValue } from '@vuelidate/validators';
import SongService from '@/services/SongService.js';

export default {
  name: 'SongEditor',
  props: {
    mode: String,
    songData: { type: Object, required: true }
  },
  emits: ['close', 'song-updated'],

  setup(props, { emit }) {
    const song = reactive({ ...props.songData });
    const fileName = ref('');
    const errorMessages = ref({});

    watch(() => props.songData, (newSong) => {
      Object.assign(song, newSong);
    });

    const rules = {
      title: { required },
      artist: { required },
      genre: { required },
      length: { required, numeric, minValue: minValue(0.01) }
    };

    const v$ = useVuelidate(rules, song);

    const handleFileUpload = (event) => {
      const file = event.target.files[0];
      if (file) {
        fileName.value = file.name;
        const reader = new FileReader();
        reader.onload = (e) => (song.fileData = e.target.result);
        reader.readAsDataURL(file);
      }
    };

    const submitForm = async () => {
      errorMessages.value = {};
      v$.value.$touch();
      if (v$.value.$invalid || !song.fileData) {
        if (!song.fileData) {
          errorMessages.value.fileData = 'Please upload a song file.';
        }
        return;
      }

      try {
        if (props.mode === 'edit' && song.id) {
          // Update existing song
          await SongService.updateSong(song.id, song);
          emit('song-updated', song);
        } else {
          // Create a new song
          const response = await SongService.createSong(song);
          alert('Song successfully created!');
          emit('song-updated', response.data);
        }
        closeForm();
      } catch (error) {
        console.log('API Fehler:', error);
        errorMessages.value = error.response?.data || { general: 'Failed to save song.' };
      }
    };

    const closeForm = () => emit('close');

    return {
      song,
      fileName,
      errorMessages,
      handleFileUpload,
      submitForm,
      closeForm,
      v$
    };
  }
};
</script>

<style scoped>
.error-message {
  color: red;
  font-size: 0.875rem;
}
</style>

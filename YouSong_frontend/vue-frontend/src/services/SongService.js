import axios from 'axios';

const API_URL = 'http://localhost:8080/api/songs';

export default {
  updateSong(id, song) {
    return axios.put(`${API_URL}/${id}`, song);
  },
  createSong(song) {
    return axios.post(API_URL, song);
  },
  getSongs() {
    return axios.get(API_URL);
  },
  deleteSong(id) {
    return axios.delete(`${API_URL}/${id}`);
  },
  getSongData(id) {
    return axios.get(`${API_URL}/${id}/play`);
  }
};

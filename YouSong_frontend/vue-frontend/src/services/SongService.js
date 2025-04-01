import axios from 'axios';

const API_URL = 'http://localhost:8080/api/songs';

export default {
    getSongs(page = 0) {
        return axios.get(`${API_URL}?page=${page}`);
    },
    searchSongs(keyword) {
        return axios.get(`${API_URL}/search?keyword=${keyword}`);
    },
    createSong(song) {
        return axios.post(API_URL, song);
    },
    getSongData(id) {
        return axios.get(`${API_URL}/data/${id}`);
    },
    updateSong(id, song) {
        return axios.put(`${API_URL}/${id}`, song);
    },
    deleteSong(id) {
        return axios.delete(`${API_URL}/${id}`);
    }
};

import axios from "axios";

const SONG_API_BASE_URL = 'http://localhost:8080/api/songs';

class SongService {

    getSongs() {
        return axios.get(SONG_API_BASE_URL);
    }

    createSong(song) {
        return axios.post(SONG_API_BASE_URL, song);
    }

    updateSong(id, song) {
        return axios.put(`${SONG_API_BASE_URL}/${id}`, song);
    }

    deleteSong(id) {
        return axios.delete(`${SONG_API_BASE_URL}/${id}`);
    }

    searchSongs(keyword) {
        return axios.get(`${SONG_API_BASE_URL}/search`, { params: { keyword } });
    }
}

export default new SongService();

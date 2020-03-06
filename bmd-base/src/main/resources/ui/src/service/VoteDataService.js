import axios from 'axios'

const HOST = 'http://localhost:8080'
const VOTE_CREATE_URL = `${HOST}/vote/start`
const VOTE_ADD_VOTE_URL = `${HOST}/vote/add`
const VOTE_GET_URL = `${HOST}/vote/get`

class VoteDataService {

    createVote(data) {
        return axios.post(`${VOTE_CREATE_URL}`, data);
    }

    getVote(voteId) {
        console.log("Get votes by id "  + voteId);
        return axios.get(`${VOTE_GET_URL}/${voteId}`);
    }

    addVote(data, voteId) {
        return axios.post(`${VOTE_ADD_VOTE_URL}/${voteId}`, data);
    }

}
export default new VoteDataService()
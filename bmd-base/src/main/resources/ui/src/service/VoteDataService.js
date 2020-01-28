import axios from 'axios'

const HOST = 'http://localhost:8080'
const VOTE_CREATE_URL = `${HOST}/vote/start`

class VoteDataService {

    createVote(data) {
        return axios.post(`${VOTE_CREATE_URL}`, data);
    }

}
export default new VoteDataService()
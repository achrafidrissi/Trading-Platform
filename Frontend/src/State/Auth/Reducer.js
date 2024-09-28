import { GET_USER_FAILURE, GET_USER_SUCCESS, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT } from "./ActionType";
import { REGISTER_REQUEST, REGISTER_SUCCESS, REGISTER_FAILURE } from "./ActionType";

const initialState = {
    user: null,
    loading: false,
    error: null,
    jwt: null,
};

const authReducer = (state = initialState, action) => {
    switch (action.type) {

        case LOGIN_REQUEST:
        case REGISTER_REQUEST:
            return { 
                ...state, 
                loading: true, 
                error: null 
            };

        case REGISTER_SUCCESS:
        case LOGIN_SUCCESS:
            return { 
                ...state, 
                loading: false, 
                user: action.payload.user, 
                jwt: action.payload.jwt, 
                error: null 
            };

        case LOGOUT:
            return initialState;             

        case GET_USER_SUCCESS:
            return {...state, user:action.payload, loading: false,error: null }

        case LOGIN_FAILURE:
        case REGISTER_FAILURE:
        case GET_USER_FAILURE:
            return { 
                ...state, 
                loading: false, 
                error: action.payload 
            };

        default:
            return state;
    }
};

export default authReducer
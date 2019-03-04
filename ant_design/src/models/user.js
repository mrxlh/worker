import { query as queryUsers, queryCurrent } from '@/services/user';
import { getWorkerList,getWorkerNameAndStatus,workerResetTask,workerRxecuteTask} from '@/services/api';

export default {
  namespace: 'user',

  state: {
    list: [],
    currentUser: {},
    worker: {},
  },

  effects: {
    *fetch(_, { call, put }) {
      const response = yield call(queryUsers);
      yield put({
        type: 'save',
        payload: response,
      });
    },
    *fetchCurrent(_, { call, put }) {
      const response = yield call(queryCurrent);
      yield put({
        type: 'saveCurrentUser',
        payload: response,
      });
    },
    *fetchWokerList({ payload }, { call, put }) {
      const response = yield call(getWorkerList, payload);
      yield put({
        type: 'saveWorkerList',
        payload: response,
      });
    },

    *fetchWokerNameAndStatus(_, { call, put }) {
      const response = yield call(getWorkerNameAndStatus);
      yield put({
        type: 'saveWorkerNameAndStatus',
        payload: response,
      });
    },

     *workerResetTask({ payload, callback }, { call, put }) {
        const response = yield call(workerResetTask,payload);
        if (callback) callback();
    },

     *workerRxecuteTask({ payload,callback }, { call, put }) {
        const response = yield call(workerRxecuteTask,payload);
        if (callback) callback();
    },
  },
  

  reducers: {
    save(state, action) {
      return {
        ...state,
        list: action.payload,
      };
    },
    saveCurrentUser(state, action) {
      return {
        ...state,
        currentUser: action.payload || {},
      };
    },
    saveWorkerList(state, action) {
      return {
        ...state,
        worker: action.payload || {},
      };
    },

     saveWorkerNameAndStatus(state, action) {
      return {
        ...state,
        workerNameAndStatus: action.payload.data || {},
      };
    },

    changeNotifyCount(state, action) {
      return {
        ...state,
        currentUser: {
          ...state.currentUser,
          notifyCount: action.payload.totalCount,
          unreadCount: action.payload.unreadCount,
        },
      };
    },
  },
};

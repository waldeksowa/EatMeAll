export const SERVER_URL = 'localhost:8080'

export const DEFAULT_PREFIX = 'http://' + SERVER_URL + '/api/v1/'
export const DEFAULT_PREFIX_V2 = 'http://' + SERVER_URL + '/api/v2/'

export const MEALS = DEFAULT_PREFIX_V2 + 'meals/'
export const ALL_PRODUCTS = DEFAULT_PREFIX + 'products/'
export const SCHEDULE = DEFAULT_PREFIX_V2 + 'schedules/'
export const MEMBER_SCHEDULE = SCHEDULE + 'member/'
export const RANDOMSCHEDULE = SCHEDULE + "random"
export const LOGIN = DEFAULT_PREFIX_V2 + 'login'
export const MEMBER = DEFAULT_PREFIX_V2 + 'members'


import { createSelector } from '@ngrx/store';
import { authFeature } from './auth.reducer';

const { selectFsAuthState } = authFeature;

const session = createSelector(selectFsAuthState, state => state.session);

const sessionLoaded = createSelector(session, session => !session.user.anonym);

const user = createSelector(selectFsAuthState, state => state.session.user);

const isAuthorized = createSelector(user, user => {
    return user.roles.filter(r => 'ADMIN' === r).length > 0;
});

export const fromAuth = {
    session,
    sessionLoaded,
    user,
    isAuthorized,
};

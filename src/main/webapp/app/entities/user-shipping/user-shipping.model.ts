import { BaseEntity, User } from './../../shared';

export class UserShipping implements BaseEntity {
    constructor(
        public id?: number,
        public userShippingName?: string,
        public userShippingStreet1?: string,
        public userShippingStreet2?: string,
        public userShippingCity?: string,
        public userShippingState?: string,
        public userShippingCountry?: string,
        public userShippingZipcode?: string,
        public userShippingDefault?: boolean,
        public user?: User,
    ) {
        this.userShippingDefault = false;
    }
}

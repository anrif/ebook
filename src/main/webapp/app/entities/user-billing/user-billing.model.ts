import { BaseEntity, User } from './../../shared';

export class UserBilling implements BaseEntity {
    constructor(
        public id?: number,
        public userBillingName?: string,
        public userBillingStreet1?: string,
        public userBillingStreet2?: string,
        public userBillingCity?: string,
        public userBillingState?: string,
        public userBillingCountry?: string,
        public userBillingZipcode?: string,
        public user?: User,
    ) {
    }
}

import { BaseEntity, User } from './../../shared';

export const enum TypePayment {
    'VISA',
    'PAYPAL',
    'MASTERCARD'
}

export class UserPayment implements BaseEntity {
    constructor(
        public id?: number,
        public type?: TypePayment,
        public cardName?: string,
        public cardNumber?: string,
        public expiryMonth?: number,
        public expiryYear?: number,
        public cvc?: number,
        public holderName?: string,
        public defaultPayment?: boolean,
        public user?: User,
    ) {
        this.defaultPayment = false;
    }
}

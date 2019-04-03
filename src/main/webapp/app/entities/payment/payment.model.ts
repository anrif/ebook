import { BaseEntity } from './../../shared';

export class Payment implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public cartName?: string,
        public cardNumber?: string,
        public expiryMonth?: number,
        public expiryYear?: number,
        public cvc?: number,
        public holderName?: string,
        public ordereds?: BaseEntity[],
    ) {
    }
}

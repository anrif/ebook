import { BaseEntity } from './../../shared';

export class Ordered implements BaseEntity {
    constructor(
        public id?: number,
        public orderDate?: any,
        public shippingDate?: any,
        public shippingMethod?: string,
        public orderStatus?: string,
        public orderTotal?: number,
        public shippingAddressses?: BaseEntity[],
        public billingAddressses?: BaseEntity[],
        public payments?: BaseEntity[],
    ) {
    }
}

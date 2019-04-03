import { BaseEntity, User } from './../../shared';

export class ShoppingCart implements BaseEntity {
    constructor(
        public id?: number,
        public grandTotal?: number,
        public user?: User,
    ) {
    }
}

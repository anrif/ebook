import { BaseEntity } from './../../shared';

export class CartItem implements BaseEntity {
    constructor(
        public id?: number,
        public qty?: number,
        public subtotal?: number,
        public book?: BaseEntity,
        public shoppingCart?: BaseEntity,
        public ordered?: BaseEntity,
    ) {
    }
}

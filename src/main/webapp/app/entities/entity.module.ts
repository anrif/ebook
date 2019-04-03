import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ArticleBillingAddressModule } from './billing-address/billing-address.module';
import { ArticleBookModule } from './book/book.module';
import { ArticleCartItemModule } from './cart-item/cart-item.module';
import { ArticleOrderedModule } from './ordered/ordered.module';
import { ArticlePaymentModule } from './payment/payment.module';
import { ArticleShippingAddressModule } from './shipping-address/shipping-address.module';
import { ArticleShoppingCartModule } from './shopping-cart/shopping-cart.module';
import { ArticleUserBillingModule } from './user-billing/user-billing.module';
import { ArticleUserPaymentModule } from './user-payment/user-payment.module';
import { ArticleUserShippingModule } from './user-shipping/user-shipping.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ArticleBillingAddressModule,
        ArticleBookModule,
        ArticleCartItemModule,
        ArticleOrderedModule,
        ArticlePaymentModule,
        ArticleShippingAddressModule,
        ArticleShoppingCartModule,
        ArticleUserBillingModule,
        ArticleUserPaymentModule,
        ArticleUserShippingModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleEntityModule {}

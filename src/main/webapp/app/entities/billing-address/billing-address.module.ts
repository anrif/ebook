import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArticleSharedModule } from '../../shared';
import {
    BillingAddressService,
    BillingAddressPopupService,
    BillingAddressComponent,
    BillingAddressDetailComponent,
    BillingAddressDialogComponent,
    BillingAddressPopupComponent,
    BillingAddressDeletePopupComponent,
    BillingAddressDeleteDialogComponent,
    billingAddressRoute,
    billingAddressPopupRoute,
    BillingAddressResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...billingAddressRoute,
    ...billingAddressPopupRoute,
];

@NgModule({
    imports: [
        ArticleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BillingAddressComponent,
        BillingAddressDetailComponent,
        BillingAddressDialogComponent,
        BillingAddressDeleteDialogComponent,
        BillingAddressPopupComponent,
        BillingAddressDeletePopupComponent,
    ],
    entryComponents: [
        BillingAddressComponent,
        BillingAddressDialogComponent,
        BillingAddressPopupComponent,
        BillingAddressDeleteDialogComponent,
        BillingAddressDeletePopupComponent,
    ],
    providers: [
        BillingAddressService,
        BillingAddressPopupService,
        BillingAddressResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleBillingAddressModule {}

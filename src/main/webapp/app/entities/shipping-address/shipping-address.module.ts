import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArticleSharedModule } from '../../shared';
import {
    ShippingAddressService,
    ShippingAddressPopupService,
    ShippingAddressComponent,
    ShippingAddressDetailComponent,
    ShippingAddressDialogComponent,
    ShippingAddressPopupComponent,
    ShippingAddressDeletePopupComponent,
    ShippingAddressDeleteDialogComponent,
    shippingAddressRoute,
    shippingAddressPopupRoute,
    ShippingAddressResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shippingAddressRoute,
    ...shippingAddressPopupRoute,
];

@NgModule({
    imports: [
        ArticleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ShippingAddressComponent,
        ShippingAddressDetailComponent,
        ShippingAddressDialogComponent,
        ShippingAddressDeleteDialogComponent,
        ShippingAddressPopupComponent,
        ShippingAddressDeletePopupComponent,
    ],
    entryComponents: [
        ShippingAddressComponent,
        ShippingAddressDialogComponent,
        ShippingAddressPopupComponent,
        ShippingAddressDeleteDialogComponent,
        ShippingAddressDeletePopupComponent,
    ],
    providers: [
        ShippingAddressService,
        ShippingAddressPopupService,
        ShippingAddressResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleShippingAddressModule {}

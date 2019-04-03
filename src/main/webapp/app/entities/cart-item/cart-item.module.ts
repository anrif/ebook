import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArticleSharedModule } from '../../shared';
import {
    CartItemService,
    CartItemPopupService,
    CartItemComponent,
    CartItemDetailComponent,
    CartItemDialogComponent,
    CartItemPopupComponent,
    CartItemDeletePopupComponent,
    CartItemDeleteDialogComponent,
    cartItemRoute,
    cartItemPopupRoute,
    CartItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...cartItemRoute,
    ...cartItemPopupRoute,
];

@NgModule({
    imports: [
        ArticleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CartItemComponent,
        CartItemDetailComponent,
        CartItemDialogComponent,
        CartItemDeleteDialogComponent,
        CartItemPopupComponent,
        CartItemDeletePopupComponent,
    ],
    entryComponents: [
        CartItemComponent,
        CartItemDialogComponent,
        CartItemPopupComponent,
        CartItemDeleteDialogComponent,
        CartItemDeletePopupComponent,
    ],
    providers: [
        CartItemService,
        CartItemPopupService,
        CartItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleCartItemModule {}

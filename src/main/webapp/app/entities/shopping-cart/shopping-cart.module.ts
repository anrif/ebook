import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArticleSharedModule } from '../../shared';
import { ArticleAdminModule } from '../../admin/admin.module';
import {
    ShoppingCartService,
    ShoppingCartPopupService,
    ShoppingCartComponent,
    ShoppingCartDetailComponent,
    ShoppingCartDialogComponent,
    ShoppingCartPopupComponent,
    ShoppingCartDeletePopupComponent,
    ShoppingCartDeleteDialogComponent,
    shoppingCartRoute,
    shoppingCartPopupRoute,
    ShoppingCartResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shoppingCartRoute,
    ...shoppingCartPopupRoute,
];

@NgModule({
    imports: [
        ArticleSharedModule,
        ArticleAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ShoppingCartComponent,
        ShoppingCartDetailComponent,
        ShoppingCartDialogComponent,
        ShoppingCartDeleteDialogComponent,
        ShoppingCartPopupComponent,
        ShoppingCartDeletePopupComponent,
    ],
    entryComponents: [
        ShoppingCartComponent,
        ShoppingCartDialogComponent,
        ShoppingCartPopupComponent,
        ShoppingCartDeleteDialogComponent,
        ShoppingCartDeletePopupComponent,
    ],
    providers: [
        ShoppingCartService,
        ShoppingCartPopupService,
        ShoppingCartResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleShoppingCartModule {}

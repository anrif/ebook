import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArticleSharedModule } from '../../shared';
import { ArticleAdminModule } from '../../admin/admin.module';
import {
    UserShippingService,
    UserShippingPopupService,
    UserShippingComponent,
    UserShippingDetailComponent,
    UserShippingDialogComponent,
    UserShippingPopupComponent,
    UserShippingDeletePopupComponent,
    UserShippingDeleteDialogComponent,
    userShippingRoute,
    userShippingPopupRoute,
    UserShippingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...userShippingRoute,
    ...userShippingPopupRoute,
];

@NgModule({
    imports: [
        ArticleSharedModule,
        ArticleAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserShippingComponent,
        UserShippingDetailComponent,
        UserShippingDialogComponent,
        UserShippingDeleteDialogComponent,
        UserShippingPopupComponent,
        UserShippingDeletePopupComponent,
    ],
    entryComponents: [
        UserShippingComponent,
        UserShippingDialogComponent,
        UserShippingPopupComponent,
        UserShippingDeleteDialogComponent,
        UserShippingDeletePopupComponent,
    ],
    providers: [
        UserShippingService,
        UserShippingPopupService,
        UserShippingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleUserShippingModule {}

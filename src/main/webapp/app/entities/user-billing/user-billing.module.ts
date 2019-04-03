import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArticleSharedModule } from '../../shared';
import { ArticleAdminModule } from '../../admin/admin.module';
import {
    UserBillingService,
    UserBillingPopupService,
    UserBillingComponent,
    UserBillingDetailComponent,
    UserBillingDialogComponent,
    UserBillingPopupComponent,
    UserBillingDeletePopupComponent,
    UserBillingDeleteDialogComponent,
    userBillingRoute,
    userBillingPopupRoute,
    UserBillingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...userBillingRoute,
    ...userBillingPopupRoute,
];

@NgModule({
    imports: [
        ArticleSharedModule,
        ArticleAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserBillingComponent,
        UserBillingDetailComponent,
        UserBillingDialogComponent,
        UserBillingDeleteDialogComponent,
        UserBillingPopupComponent,
        UserBillingDeletePopupComponent,
    ],
    entryComponents: [
        UserBillingComponent,
        UserBillingDialogComponent,
        UserBillingPopupComponent,
        UserBillingDeleteDialogComponent,
        UserBillingDeletePopupComponent,
    ],
    providers: [
        UserBillingService,
        UserBillingPopupService,
        UserBillingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleUserBillingModule {}

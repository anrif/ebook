import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArticleSharedModule } from '../../shared';
import { ArticleAdminModule } from '../../admin/admin.module';
import {
    UserPaymentService,
    UserPaymentPopupService,
    UserPaymentComponent,
    UserPaymentDetailComponent,
    UserPaymentDialogComponent,
    UserPaymentPopupComponent,
    UserPaymentDeletePopupComponent,
    UserPaymentDeleteDialogComponent,
    userPaymentRoute,
    userPaymentPopupRoute,
    UserPaymentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...userPaymentRoute,
    ...userPaymentPopupRoute,
];

@NgModule({
    imports: [
        ArticleSharedModule,
        ArticleAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserPaymentComponent,
        UserPaymentDetailComponent,
        UserPaymentDialogComponent,
        UserPaymentDeleteDialogComponent,
        UserPaymentPopupComponent,
        UserPaymentDeletePopupComponent,
    ],
    entryComponents: [
        UserPaymentComponent,
        UserPaymentDialogComponent,
        UserPaymentPopupComponent,
        UserPaymentDeleteDialogComponent,
        UserPaymentDeletePopupComponent,
    ],
    providers: [
        UserPaymentService,
        UserPaymentPopupService,
        UserPaymentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleUserPaymentModule {}

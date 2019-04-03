import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArticleSharedModule } from '../../shared';
import {
    OrderedService,
    OrderedPopupService,
    OrderedComponent,
    OrderedDetailComponent,
    OrderedDialogComponent,
    OrderedPopupComponent,
    OrderedDeletePopupComponent,
    OrderedDeleteDialogComponent,
    orderedRoute,
    orderedPopupRoute,
    OrderedResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderedRoute,
    ...orderedPopupRoute,
];

@NgModule({
    imports: [
        ArticleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrderedComponent,
        OrderedDetailComponent,
        OrderedDialogComponent,
        OrderedDeleteDialogComponent,
        OrderedPopupComponent,
        OrderedDeletePopupComponent,
    ],
    entryComponents: [
        OrderedComponent,
        OrderedDialogComponent,
        OrderedPopupComponent,
        OrderedDeleteDialogComponent,
        OrderedDeletePopupComponent,
    ],
    providers: [
        OrderedService,
        OrderedPopupService,
        OrderedResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleOrderedModule {}

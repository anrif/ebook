import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { UserBillingComponent } from './user-billing.component';
import { UserBillingDetailComponent } from './user-billing-detail.component';
import { UserBillingPopupComponent } from './user-billing-dialog.component';
import { UserBillingDeletePopupComponent } from './user-billing-delete-dialog.component';

@Injectable()
export class UserBillingResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const userBillingRoute: Routes = [
    {
        path: 'user-billing',
        component: UserBillingComponent,
        resolve: {
            'pagingParams': UserBillingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userBilling.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-billing/:id',
        component: UserBillingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userBilling.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userBillingPopupRoute: Routes = [
    {
        path: 'user-billing-new',
        component: UserBillingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userBilling.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-billing/:id/edit',
        component: UserBillingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userBilling.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-billing/:id/delete',
        component: UserBillingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userBilling.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

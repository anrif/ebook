import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { UserPaymentComponent } from './user-payment.component';
import { UserPaymentDetailComponent } from './user-payment-detail.component';
import { UserPaymentPopupComponent } from './user-payment-dialog.component';
import { UserPaymentDeletePopupComponent } from './user-payment-delete-dialog.component';

@Injectable()
export class UserPaymentResolvePagingParams implements Resolve<any> {

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

export const userPaymentRoute: Routes = [
    {
        path: 'user-payment',
        component: UserPaymentComponent,
        resolve: {
            'pagingParams': UserPaymentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userPayment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-payment/:id',
        component: UserPaymentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userPayment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userPaymentPopupRoute: Routes = [
    {
        path: 'user-payment-new',
        component: UserPaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userPayment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-payment/:id/edit',
        component: UserPaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userPayment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-payment/:id/delete',
        component: UserPaymentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userPayment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { UserShippingComponent } from './user-shipping.component';
import { UserShippingDetailComponent } from './user-shipping-detail.component';
import { UserShippingPopupComponent } from './user-shipping-dialog.component';
import { UserShippingDeletePopupComponent } from './user-shipping-delete-dialog.component';

@Injectable()
export class UserShippingResolvePagingParams implements Resolve<any> {

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

export const userShippingRoute: Routes = [
    {
        path: 'user-shipping',
        component: UserShippingComponent,
        resolve: {
            'pagingParams': UserShippingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userShipping.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-shipping/:id',
        component: UserShippingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userShipping.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userShippingPopupRoute: Routes = [
    {
        path: 'user-shipping-new',
        component: UserShippingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userShipping.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-shipping/:id/edit',
        component: UserShippingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userShipping.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-shipping/:id/delete',
        component: UserShippingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.userShipping.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

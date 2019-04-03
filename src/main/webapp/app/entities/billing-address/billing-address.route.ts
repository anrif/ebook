import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BillingAddressComponent } from './billing-address.component';
import { BillingAddressDetailComponent } from './billing-address-detail.component';
import { BillingAddressPopupComponent } from './billing-address-dialog.component';
import { BillingAddressDeletePopupComponent } from './billing-address-delete-dialog.component';

@Injectable()
export class BillingAddressResolvePagingParams implements Resolve<any> {

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

export const billingAddressRoute: Routes = [
    {
        path: 'billing-address',
        component: BillingAddressComponent,
        resolve: {
            'pagingParams': BillingAddressResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.billingAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'billing-address/:id',
        component: BillingAddressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.billingAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const billingAddressPopupRoute: Routes = [
    {
        path: 'billing-address-new',
        component: BillingAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.billingAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'billing-address/:id/edit',
        component: BillingAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.billingAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'billing-address/:id/delete',
        component: BillingAddressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.billingAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

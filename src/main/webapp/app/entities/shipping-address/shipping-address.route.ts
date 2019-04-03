import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ShippingAddressComponent } from './shipping-address.component';
import { ShippingAddressDetailComponent } from './shipping-address-detail.component';
import { ShippingAddressPopupComponent } from './shipping-address-dialog.component';
import { ShippingAddressDeletePopupComponent } from './shipping-address-delete-dialog.component';

@Injectable()
export class ShippingAddressResolvePagingParams implements Resolve<any> {

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

export const shippingAddressRoute: Routes = [
    {
        path: 'shipping-address',
        component: ShippingAddressComponent,
        resolve: {
            'pagingParams': ShippingAddressResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shippingAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shipping-address/:id',
        component: ShippingAddressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shippingAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shippingAddressPopupRoute: Routes = [
    {
        path: 'shipping-address-new',
        component: ShippingAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shippingAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipping-address/:id/edit',
        component: ShippingAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shippingAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipping-address/:id/delete',
        component: ShippingAddressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shippingAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

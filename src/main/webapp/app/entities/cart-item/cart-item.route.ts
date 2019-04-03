import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CartItemComponent } from './cart-item.component';
import { CartItemDetailComponent } from './cart-item-detail.component';
import { CartItemPopupComponent } from './cart-item-dialog.component';
import { CartItemDeletePopupComponent } from './cart-item-delete-dialog.component';

@Injectable()
export class CartItemResolvePagingParams implements Resolve<any> {

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

export const cartItemRoute: Routes = [
    {
        path: 'cart-item',
        component: CartItemComponent,
        resolve: {
            'pagingParams': CartItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cart-item/:id',
        component: CartItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cartItemPopupRoute: Routes = [
    {
        path: 'cart-item-new',
        component: CartItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cart-item/:id/edit',
        component: CartItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cart-item/:id/delete',
        component: CartItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.cartItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

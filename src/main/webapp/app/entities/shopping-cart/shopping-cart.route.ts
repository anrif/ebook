import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ShoppingCartComponent } from './shopping-cart.component';
import { ShoppingCartDetailComponent } from './shopping-cart-detail.component';
import { ShoppingCartPopupComponent } from './shopping-cart-dialog.component';
import { ShoppingCartDeletePopupComponent } from './shopping-cart-delete-dialog.component';

@Injectable()
export class ShoppingCartResolvePagingParams implements Resolve<any> {

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

export const shoppingCartRoute: Routes = [
    {
        path: 'shopping-cart',
        component: ShoppingCartComponent,
        resolve: {
            'pagingParams': ShoppingCartResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shopping-cart/:id',
        component: ShoppingCartDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shoppingCartPopupRoute: Routes = [
    {
        path: 'shopping-cart-new',
        component: ShoppingCartPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shopping-cart/:id/edit',
        component: ShoppingCartPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shopping-cart/:id/delete',
        component: ShoppingCartDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.shoppingCart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OrderedComponent } from './ordered.component';
import { OrderedDetailComponent } from './ordered-detail.component';
import { OrderedPopupComponent } from './ordered-dialog.component';
import { OrderedDeletePopupComponent } from './ordered-delete-dialog.component';

@Injectable()
export class OrderedResolvePagingParams implements Resolve<any> {

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

export const orderedRoute: Routes = [
    {
        path: 'ordered',
        component: OrderedComponent,
        resolve: {
            'pagingParams': OrderedResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.ordered.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ordered/:id',
        component: OrderedDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.ordered.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderedPopupRoute: Routes = [
    {
        path: 'ordered-new',
        component: OrderedPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.ordered.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordered/:id/edit',
        component: OrderedPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.ordered.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordered/:id/delete',
        component: OrderedDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'articleApp.ordered.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

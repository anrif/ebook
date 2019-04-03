import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CartItem } from './cart-item.model';
import { CartItemPopupService } from './cart-item-popup.service';
import { CartItemService } from './cart-item.service';
import { Book, BookService } from '../book';
import { ShoppingCart, ShoppingCartService } from '../shopping-cart';
import { Ordered, OrderedService } from '../ordered';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-cart-item-dialog',
    templateUrl: './cart-item-dialog.component.html'
})
export class CartItemDialogComponent implements OnInit {

    cartItem: CartItem;
    isSaving: boolean;

    books: Book[];

    shoppingcarts: ShoppingCart[];

    ordereds: Ordered[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cartItemService: CartItemService,
        private bookService: BookService,
        private shoppingCartService: ShoppingCartService,
        private orderedService: OrderedService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.bookService.query()
            .subscribe((res: ResponseWrapper) => { this.books = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.shoppingCartService.query()
            .subscribe((res: ResponseWrapper) => { this.shoppingcarts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.orderedService.query()
            .subscribe((res: ResponseWrapper) => { this.ordereds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cartItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cartItemService.update(this.cartItem));
        } else {
            this.subscribeToSaveResponse(
                this.cartItemService.create(this.cartItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<CartItem>) {
        result.subscribe((res: CartItem) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CartItem) {
        this.eventManager.broadcast({ name: 'cartItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBookById(index: number, item: Book) {
        return item.id;
    }

    trackShoppingCartById(index: number, item: ShoppingCart) {
        return item.id;
    }

    trackOrderedById(index: number, item: Ordered) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cart-item-popup',
    template: ''
})
export class CartItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cartItemPopupService: CartItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cartItemPopupService
                    .open(CartItemDialogComponent as Component, params['id']);
            } else {
                this.cartItemPopupService
                    .open(CartItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

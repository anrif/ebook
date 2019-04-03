import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CartItem } from './cart-item.model';
import { CartItemService } from './cart-item.service';

@Component({
    selector: 'jhi-cart-item-detail',
    templateUrl: './cart-item-detail.component.html'
})
export class CartItemDetailComponent implements OnInit, OnDestroy {

    cartItem: CartItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cartItemService: CartItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCartItems();
    }

    load(id) {
        this.cartItemService.find(id).subscribe((cartItem) => {
            this.cartItem = cartItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCartItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cartItemListModification',
            (response) => this.load(this.cartItem.id)
        );
    }
}

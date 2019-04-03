import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CartItem } from './cart-item.model';
import { CartItemPopupService } from './cart-item-popup.service';
import { CartItemService } from './cart-item.service';

@Component({
    selector: 'jhi-cart-item-delete-dialog',
    templateUrl: './cart-item-delete-dialog.component.html'
})
export class CartItemDeleteDialogComponent {

    cartItem: CartItem;

    constructor(
        private cartItemService: CartItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cartItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cartItemListModification',
                content: 'Deleted an cartItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cart-item-delete-popup',
    template: ''
})
export class CartItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cartItemPopupService: CartItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cartItemPopupService
                .open(CartItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

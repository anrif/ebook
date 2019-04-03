import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShippingAddress } from './shipping-address.model';
import { ShippingAddressPopupService } from './shipping-address-popup.service';
import { ShippingAddressService } from './shipping-address.service';

@Component({
    selector: 'jhi-shipping-address-delete-dialog',
    templateUrl: './shipping-address-delete-dialog.component.html'
})
export class ShippingAddressDeleteDialogComponent {

    shippingAddress: ShippingAddress;

    constructor(
        private shippingAddressService: ShippingAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shippingAddressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shippingAddressListModification',
                content: 'Deleted an shippingAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shipping-address-delete-popup',
    template: ''
})
export class ShippingAddressDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shippingAddressPopupService: ShippingAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shippingAddressPopupService
                .open(ShippingAddressDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

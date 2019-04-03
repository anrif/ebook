import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BillingAddress } from './billing-address.model';
import { BillingAddressPopupService } from './billing-address-popup.service';
import { BillingAddressService } from './billing-address.service';

@Component({
    selector: 'jhi-billing-address-delete-dialog',
    templateUrl: './billing-address-delete-dialog.component.html'
})
export class BillingAddressDeleteDialogComponent {

    billingAddress: BillingAddress;

    constructor(
        private billingAddressService: BillingAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.billingAddressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'billingAddressListModification',
                content: 'Deleted an billingAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-billing-address-delete-popup',
    template: ''
})
export class BillingAddressDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private billingAddressPopupService: BillingAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.billingAddressPopupService
                .open(BillingAddressDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

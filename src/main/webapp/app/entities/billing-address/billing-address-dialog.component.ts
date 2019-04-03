import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BillingAddress } from './billing-address.model';
import { BillingAddressPopupService } from './billing-address-popup.service';
import { BillingAddressService } from './billing-address.service';
import { Ordered, OrderedService } from '../ordered';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-billing-address-dialog',
    templateUrl: './billing-address-dialog.component.html'
})
export class BillingAddressDialogComponent implements OnInit {

    billingAddress: BillingAddress;
    isSaving: boolean;

    ordereds: Ordered[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private billingAddressService: BillingAddressService,
        private orderedService: OrderedService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.orderedService.query()
            .subscribe((res: ResponseWrapper) => { this.ordereds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.billingAddress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.billingAddressService.update(this.billingAddress));
        } else {
            this.subscribeToSaveResponse(
                this.billingAddressService.create(this.billingAddress));
        }
    }

    private subscribeToSaveResponse(result: Observable<BillingAddress>) {
        result.subscribe((res: BillingAddress) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BillingAddress) {
        this.eventManager.broadcast({ name: 'billingAddressListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrderedById(index: number, item: Ordered) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-billing-address-popup',
    template: ''
})
export class BillingAddressPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private billingAddressPopupService: BillingAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.billingAddressPopupService
                    .open(BillingAddressDialogComponent as Component, params['id']);
            } else {
                this.billingAddressPopupService
                    .open(BillingAddressDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

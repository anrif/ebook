import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ShippingAddress } from './shipping-address.model';
import { ShippingAddressPopupService } from './shipping-address-popup.service';
import { ShippingAddressService } from './shipping-address.service';
import { Ordered, OrderedService } from '../ordered';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-shipping-address-dialog',
    templateUrl: './shipping-address-dialog.component.html'
})
export class ShippingAddressDialogComponent implements OnInit {

    shippingAddress: ShippingAddress;
    isSaving: boolean;

    ordereds: Ordered[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private shippingAddressService: ShippingAddressService,
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
        if (this.shippingAddress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shippingAddressService.update(this.shippingAddress));
        } else {
            this.subscribeToSaveResponse(
                this.shippingAddressService.create(this.shippingAddress));
        }
    }

    private subscribeToSaveResponse(result: Observable<ShippingAddress>) {
        result.subscribe((res: ShippingAddress) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ShippingAddress) {
        this.eventManager.broadcast({ name: 'shippingAddressListModification', content: 'OK'});
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
    selector: 'jhi-shipping-address-popup',
    template: ''
})
export class ShippingAddressPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shippingAddressPopupService: ShippingAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shippingAddressPopupService
                    .open(ShippingAddressDialogComponent as Component, params['id']);
            } else {
                this.shippingAddressPopupService
                    .open(ShippingAddressDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

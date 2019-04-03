import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ordered } from './ordered.model';
import { OrderedPopupService } from './ordered-popup.service';
import { OrderedService } from './ordered.service';
import { ShippingAddress, ShippingAddressService } from '../shipping-address';
import { BillingAddress, BillingAddressService } from '../billing-address';
import { Payment, PaymentService } from '../payment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ordered-dialog',
    templateUrl: './ordered-dialog.component.html'
})
export class OrderedDialogComponent implements OnInit {

    ordered: Ordered;
    isSaving: boolean;

    shippingaddresses: ShippingAddress[];

    billingaddresses: BillingAddress[];

    payments: Payment[];
    orderDateDp: any;
    shippingDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private orderedService: OrderedService,
        private shippingAddressService: ShippingAddressService,
        private billingAddressService: BillingAddressService,
        private paymentService: PaymentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.shippingAddressService.query()
            .subscribe((res: ResponseWrapper) => { this.shippingaddresses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.billingAddressService.query()
            .subscribe((res: ResponseWrapper) => { this.billingaddresses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.paymentService.query()
            .subscribe((res: ResponseWrapper) => { this.payments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ordered.id !== undefined) {
            this.subscribeToSaveResponse(
                this.orderedService.update(this.ordered));
        } else {
            this.subscribeToSaveResponse(
                this.orderedService.create(this.ordered));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ordered>) {
        result.subscribe((res: Ordered) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ordered) {
        this.eventManager.broadcast({ name: 'orderedListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackShippingAddressById(index: number, item: ShippingAddress) {
        return item.id;
    }

    trackBillingAddressById(index: number, item: BillingAddress) {
        return item.id;
    }

    trackPaymentById(index: number, item: Payment) {
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
    selector: 'jhi-ordered-popup',
    template: ''
})
export class OrderedPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderedPopupService: OrderedPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.orderedPopupService
                    .open(OrderedDialogComponent as Component, params['id']);
            } else {
                this.orderedPopupService
                    .open(OrderedDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

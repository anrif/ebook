import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BillingAddress } from './billing-address.model';
import { BillingAddressService } from './billing-address.service';

@Component({
    selector: 'jhi-billing-address-detail',
    templateUrl: './billing-address-detail.component.html'
})
export class BillingAddressDetailComponent implements OnInit, OnDestroy {

    billingAddress: BillingAddress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private billingAddressService: BillingAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBillingAddresses();
    }

    load(id) {
        this.billingAddressService.find(id).subscribe((billingAddress) => {
            this.billingAddress = billingAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBillingAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'billingAddressListModification',
            (response) => this.load(this.billingAddress.id)
        );
    }
}

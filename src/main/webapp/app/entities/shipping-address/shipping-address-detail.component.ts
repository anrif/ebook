import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ShippingAddress } from './shipping-address.model';
import { ShippingAddressService } from './shipping-address.service';

@Component({
    selector: 'jhi-shipping-address-detail',
    templateUrl: './shipping-address-detail.component.html'
})
export class ShippingAddressDetailComponent implements OnInit, OnDestroy {

    shippingAddress: ShippingAddress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shippingAddressService: ShippingAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShippingAddresses();
    }

    load(id) {
        this.shippingAddressService.find(id).subscribe((shippingAddress) => {
            this.shippingAddress = shippingAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShippingAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shippingAddressListModification',
            (response) => this.load(this.shippingAddress.id)
        );
    }
}

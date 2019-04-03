import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserShipping } from './user-shipping.model';
import { UserShippingService } from './user-shipping.service';

@Component({
    selector: 'jhi-user-shipping-detail',
    templateUrl: './user-shipping-detail.component.html'
})
export class UserShippingDetailComponent implements OnInit, OnDestroy {

    userShipping: UserShipping;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userShippingService: UserShippingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserShippings();
    }

    load(id) {
        this.userShippingService.find(id).subscribe((userShipping) => {
            this.userShipping = userShipping;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserShippings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userShippingListModification',
            (response) => this.load(this.userShipping.id)
        );
    }
}

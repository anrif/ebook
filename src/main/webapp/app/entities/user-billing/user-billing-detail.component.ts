import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserBilling } from './user-billing.model';
import { UserBillingService } from './user-billing.service';

@Component({
    selector: 'jhi-user-billing-detail',
    templateUrl: './user-billing-detail.component.html'
})
export class UserBillingDetailComponent implements OnInit, OnDestroy {

    userBilling: UserBilling;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userBillingService: UserBillingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserBillings();
    }

    load(id) {
        this.userBillingService.find(id).subscribe((userBilling) => {
            this.userBilling = userBilling;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserBillings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userBillingListModification',
            (response) => this.load(this.userBilling.id)
        );
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserPayment } from './user-payment.model';
import { UserPaymentService } from './user-payment.service';

@Component({
    selector: 'jhi-user-payment-detail',
    templateUrl: './user-payment-detail.component.html'
})
export class UserPaymentDetailComponent implements OnInit, OnDestroy {

    userPayment: UserPayment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userPaymentService: UserPaymentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserPayments();
    }

    load(id) {
        this.userPaymentService.find(id).subscribe((userPayment) => {
            this.userPayment = userPayment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserPayments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userPaymentListModification',
            (response) => this.load(this.userPayment.id)
        );
    }
}

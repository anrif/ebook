import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserPayment } from './user-payment.model';
import { UserPaymentPopupService } from './user-payment-popup.service';
import { UserPaymentService } from './user-payment.service';

@Component({
    selector: 'jhi-user-payment-delete-dialog',
    templateUrl: './user-payment-delete-dialog.component.html'
})
export class UserPaymentDeleteDialogComponent {

    userPayment: UserPayment;

    constructor(
        private userPaymentService: UserPaymentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userPaymentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userPaymentListModification',
                content: 'Deleted an userPayment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-payment-delete-popup',
    template: ''
})
export class UserPaymentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPaymentPopupService: UserPaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userPaymentPopupService
                .open(UserPaymentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

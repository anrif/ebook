import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserPayment } from './user-payment.model';
import { UserPaymentPopupService } from './user-payment-popup.service';
import { UserPaymentService } from './user-payment.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-payment-dialog',
    templateUrl: './user-payment-dialog.component.html'
})
export class UserPaymentDialogComponent implements OnInit {

    userPayment: UserPayment;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userPaymentService: UserPaymentService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userPayment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userPaymentService.update(this.userPayment));
        } else {
            this.subscribeToSaveResponse(
                this.userPaymentService.create(this.userPayment));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserPayment>) {
        result.subscribe((res: UserPayment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserPayment) {
        this.eventManager.broadcast({ name: 'userPaymentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-payment-popup',
    template: ''
})
export class UserPaymentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPaymentPopupService: UserPaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userPaymentPopupService
                    .open(UserPaymentDialogComponent as Component, params['id']);
            } else {
                this.userPaymentPopupService
                    .open(UserPaymentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

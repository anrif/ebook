import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserBilling } from './user-billing.model';
import { UserBillingPopupService } from './user-billing-popup.service';
import { UserBillingService } from './user-billing.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-billing-dialog',
    templateUrl: './user-billing-dialog.component.html'
})
export class UserBillingDialogComponent implements OnInit {

    userBilling: UserBilling;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userBillingService: UserBillingService,
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
        if (this.userBilling.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userBillingService.update(this.userBilling));
        } else {
            this.subscribeToSaveResponse(
                this.userBillingService.create(this.userBilling));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserBilling>) {
        result.subscribe((res: UserBilling) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserBilling) {
        this.eventManager.broadcast({ name: 'userBillingListModification', content: 'OK'});
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
    selector: 'jhi-user-billing-popup',
    template: ''
})
export class UserBillingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userBillingPopupService: UserBillingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userBillingPopupService
                    .open(UserBillingDialogComponent as Component, params['id']);
            } else {
                this.userBillingPopupService
                    .open(UserBillingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

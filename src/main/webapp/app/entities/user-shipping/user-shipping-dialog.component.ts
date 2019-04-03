import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserShipping } from './user-shipping.model';
import { UserShippingPopupService } from './user-shipping-popup.service';
import { UserShippingService } from './user-shipping.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-shipping-dialog',
    templateUrl: './user-shipping-dialog.component.html'
})
export class UserShippingDialogComponent implements OnInit {

    userShipping: UserShipping;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userShippingService: UserShippingService,
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
        if (this.userShipping.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userShippingService.update(this.userShipping));
        } else {
            this.subscribeToSaveResponse(
                this.userShippingService.create(this.userShipping));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserShipping>) {
        result.subscribe((res: UserShipping) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserShipping) {
        this.eventManager.broadcast({ name: 'userShippingListModification', content: 'OK'});
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
    selector: 'jhi-user-shipping-popup',
    template: ''
})
export class UserShippingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userShippingPopupService: UserShippingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userShippingPopupService
                    .open(UserShippingDialogComponent as Component, params['id']);
            } else {
                this.userShippingPopupService
                    .open(UserShippingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

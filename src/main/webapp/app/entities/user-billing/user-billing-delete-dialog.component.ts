import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserBilling } from './user-billing.model';
import { UserBillingPopupService } from './user-billing-popup.service';
import { UserBillingService } from './user-billing.service';

@Component({
    selector: 'jhi-user-billing-delete-dialog',
    templateUrl: './user-billing-delete-dialog.component.html'
})
export class UserBillingDeleteDialogComponent {

    userBilling: UserBilling;

    constructor(
        private userBillingService: UserBillingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userBillingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userBillingListModification',
                content: 'Deleted an userBilling'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-billing-delete-popup',
    template: ''
})
export class UserBillingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userBillingPopupService: UserBillingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userBillingPopupService
                .open(UserBillingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

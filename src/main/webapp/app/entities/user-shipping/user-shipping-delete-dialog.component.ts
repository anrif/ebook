import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserShipping } from './user-shipping.model';
import { UserShippingPopupService } from './user-shipping-popup.service';
import { UserShippingService } from './user-shipping.service';

@Component({
    selector: 'jhi-user-shipping-delete-dialog',
    templateUrl: './user-shipping-delete-dialog.component.html'
})
export class UserShippingDeleteDialogComponent {

    userShipping: UserShipping;

    constructor(
        private userShippingService: UserShippingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userShippingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userShippingListModification',
                content: 'Deleted an userShipping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-shipping-delete-popup',
    template: ''
})
export class UserShippingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userShippingPopupService: UserShippingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userShippingPopupService
                .open(UserShippingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

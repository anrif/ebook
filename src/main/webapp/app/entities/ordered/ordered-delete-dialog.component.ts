import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ordered } from './ordered.model';
import { OrderedPopupService } from './ordered-popup.service';
import { OrderedService } from './ordered.service';

@Component({
    selector: 'jhi-ordered-delete-dialog',
    templateUrl: './ordered-delete-dialog.component.html'
})
export class OrderedDeleteDialogComponent {

    ordered: Ordered;

    constructor(
        private orderedService: OrderedService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderedService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderedListModification',
                content: 'Deleted an ordered'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ordered-delete-popup',
    template: ''
})
export class OrderedDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderedPopupService: OrderedPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.orderedPopupService
                .open(OrderedDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

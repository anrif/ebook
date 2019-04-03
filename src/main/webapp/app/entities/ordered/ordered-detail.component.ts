import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Ordered } from './ordered.model';
import { OrderedService } from './ordered.service';

@Component({
    selector: 'jhi-ordered-detail',
    templateUrl: './ordered-detail.component.html'
})
export class OrderedDetailComponent implements OnInit, OnDestroy {

    ordered: Ordered;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private orderedService: OrderedService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrdereds();
    }

    load(id) {
        this.orderedService.find(id).subscribe((ordered) => {
            this.ordered = ordered;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrdereds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'orderedListModification',
            (response) => this.load(this.ordered.id)
        );
    }
}

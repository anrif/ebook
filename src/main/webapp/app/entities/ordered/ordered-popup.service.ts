import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Ordered } from './ordered.model';
import { OrderedService } from './ordered.service';

@Injectable()
export class OrderedPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderedService: OrderedService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.orderedService.find(id).subscribe((ordered) => {
                    if (ordered.orderDate) {
                        ordered.orderDate = {
                            year: ordered.orderDate.getFullYear(),
                            month: ordered.orderDate.getMonth() + 1,
                            day: ordered.orderDate.getDate()
                        };
                    }
                    if (ordered.shippingDate) {
                        ordered.shippingDate = {
                            year: ordered.shippingDate.getFullYear(),
                            month: ordered.shippingDate.getMonth() + 1,
                            day: ordered.shippingDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.orderedModalRef(component, ordered);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.orderedModalRef(component, new Ordered());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    orderedModalRef(component: Component, ordered: Ordered): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ordered = ordered;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTestModule } from '../../../test.module';
import { BillingAddressDialogComponent } from '../../../../../../main/webapp/app/entities/billing-address/billing-address-dialog.component';
import { BillingAddressService } from '../../../../../../main/webapp/app/entities/billing-address/billing-address.service';
import { BillingAddress } from '../../../../../../main/webapp/app/entities/billing-address/billing-address.model';
import { OrderedService } from '../../../../../../main/webapp/app/entities/ordered';

describe('Component Tests', () => {

    describe('BillingAddress Management Dialog Component', () => {
        let comp: BillingAddressDialogComponent;
        let fixture: ComponentFixture<BillingAddressDialogComponent>;
        let service: BillingAddressService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [BillingAddressDialogComponent],
                providers: [
                    OrderedService,
                    BillingAddressService
                ]
            })
            .overrideTemplate(BillingAddressDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BillingAddressDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingAddressService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BillingAddress(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.billingAddress = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'billingAddressListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BillingAddress();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.billingAddress = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'billingAddressListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

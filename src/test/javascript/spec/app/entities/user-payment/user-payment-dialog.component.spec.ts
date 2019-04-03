/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTestModule } from '../../../test.module';
import { UserPaymentDialogComponent } from '../../../../../../main/webapp/app/entities/user-payment/user-payment-dialog.component';
import { UserPaymentService } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.service';
import { UserPayment } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.model';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('UserPayment Management Dialog Component', () => {
        let comp: UserPaymentDialogComponent;
        let fixture: ComponentFixture<UserPaymentDialogComponent>;
        let service: UserPaymentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserPaymentDialogComponent],
                providers: [
                    UserService,
                    UserPaymentService
                ]
            })
            .overrideTemplate(UserPaymentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPaymentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPaymentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserPayment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.userPayment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userPaymentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserPayment();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.userPayment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userPaymentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

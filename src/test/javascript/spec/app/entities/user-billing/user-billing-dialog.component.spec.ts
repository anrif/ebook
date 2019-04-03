/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTestModule } from '../../../test.module';
import { UserBillingDialogComponent } from '../../../../../../main/webapp/app/entities/user-billing/user-billing-dialog.component';
import { UserBillingService } from '../../../../../../main/webapp/app/entities/user-billing/user-billing.service';
import { UserBilling } from '../../../../../../main/webapp/app/entities/user-billing/user-billing.model';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('UserBilling Management Dialog Component', () => {
        let comp: UserBillingDialogComponent;
        let fixture: ComponentFixture<UserBillingDialogComponent>;
        let service: UserBillingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserBillingDialogComponent],
                providers: [
                    UserService,
                    UserBillingService
                ]
            })
            .overrideTemplate(UserBillingDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserBillingDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserBillingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserBilling(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.userBilling = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userBillingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserBilling();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.userBilling = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userBillingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

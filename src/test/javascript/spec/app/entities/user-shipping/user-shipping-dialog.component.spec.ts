/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTestModule } from '../../../test.module';
import { UserShippingDialogComponent } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping-dialog.component';
import { UserShippingService } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping.service';
import { UserShipping } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping.model';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('UserShipping Management Dialog Component', () => {
        let comp: UserShippingDialogComponent;
        let fixture: ComponentFixture<UserShippingDialogComponent>;
        let service: UserShippingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserShippingDialogComponent],
                providers: [
                    UserService,
                    UserShippingService
                ]
            })
            .overrideTemplate(UserShippingDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserShippingDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserShippingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserShipping(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.userShipping = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userShippingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserShipping();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.userShipping = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userShippingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

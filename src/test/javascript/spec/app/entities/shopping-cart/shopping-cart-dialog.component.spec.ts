/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTestModule } from '../../../test.module';
import { ShoppingCartDialogComponent } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart-dialog.component';
import { ShoppingCartService } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.service';
import { ShoppingCart } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.model';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('ShoppingCart Management Dialog Component', () => {
        let comp: ShoppingCartDialogComponent;
        let fixture: ComponentFixture<ShoppingCartDialogComponent>;
        let service: ShoppingCartService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [ShoppingCartDialogComponent],
                providers: [
                    UserService,
                    ShoppingCartService
                ]
            })
            .overrideTemplate(ShoppingCartDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingCartDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingCartService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ShoppingCart(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.shoppingCart = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shoppingCartListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ShoppingCart();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.shoppingCart = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shoppingCartListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

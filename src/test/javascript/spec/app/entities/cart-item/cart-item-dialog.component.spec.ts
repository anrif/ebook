/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTestModule } from '../../../test.module';
import { CartItemDialogComponent } from '../../../../../../main/webapp/app/entities/cart-item/cart-item-dialog.component';
import { CartItemService } from '../../../../../../main/webapp/app/entities/cart-item/cart-item.service';
import { CartItem } from '../../../../../../main/webapp/app/entities/cart-item/cart-item.model';
import { BookService } from '../../../../../../main/webapp/app/entities/book';
import { ShoppingCartService } from '../../../../../../main/webapp/app/entities/shopping-cart';
import { OrderedService } from '../../../../../../main/webapp/app/entities/ordered';

describe('Component Tests', () => {

    describe('CartItem Management Dialog Component', () => {
        let comp: CartItemDialogComponent;
        let fixture: ComponentFixture<CartItemDialogComponent>;
        let service: CartItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [CartItemDialogComponent],
                providers: [
                    BookService,
                    ShoppingCartService,
                    OrderedService,
                    CartItemService
                ]
            })
            .overrideTemplate(CartItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CartItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CartItem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.cartItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'cartItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CartItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.cartItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'cartItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

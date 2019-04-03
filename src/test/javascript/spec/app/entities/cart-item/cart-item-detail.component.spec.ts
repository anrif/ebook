/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { CartItemDetailComponent } from '../../../../../../main/webapp/app/entities/cart-item/cart-item-detail.component';
import { CartItemService } from '../../../../../../main/webapp/app/entities/cart-item/cart-item.service';
import { CartItem } from '../../../../../../main/webapp/app/entities/cart-item/cart-item.model';

describe('Component Tests', () => {

    describe('CartItem Management Detail Component', () => {
        let comp: CartItemDetailComponent;
        let fixture: ComponentFixture<CartItemDetailComponent>;
        let service: CartItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [CartItemDetailComponent],
                providers: [
                    CartItemService
                ]
            })
            .overrideTemplate(CartItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CartItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CartItem(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.cartItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

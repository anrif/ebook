/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ArticleTestModule } from '../../../test.module';
import { CartItemComponent } from '../../../../../../main/webapp/app/entities/cart-item/cart-item.component';
import { CartItemService } from '../../../../../../main/webapp/app/entities/cart-item/cart-item.service';
import { CartItem } from '../../../../../../main/webapp/app/entities/cart-item/cart-item.model';

describe('Component Tests', () => {

    describe('CartItem Management Component', () => {
        let comp: CartItemComponent;
        let fixture: ComponentFixture<CartItemComponent>;
        let service: CartItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [CartItemComponent],
                providers: [
                    CartItemService
                ]
            })
            .overrideTemplate(CartItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CartItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CartItem(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.cartItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

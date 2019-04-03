/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { ShoppingCartDetailComponent } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart-detail.component';
import { ShoppingCartService } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.service';
import { ShoppingCart } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.model';

describe('Component Tests', () => {

    describe('ShoppingCart Management Detail Component', () => {
        let comp: ShoppingCartDetailComponent;
        let fixture: ComponentFixture<ShoppingCartDetailComponent>;
        let service: ShoppingCartService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [ShoppingCartDetailComponent],
                providers: [
                    ShoppingCartService
                ]
            })
            .overrideTemplate(ShoppingCartDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingCartDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingCartService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ShoppingCart(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.shoppingCart).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

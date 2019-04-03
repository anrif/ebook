/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ArticleTestModule } from '../../../test.module';
import { ShoppingCartComponent } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.component';
import { ShoppingCartService } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.service';
import { ShoppingCart } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart.model';

describe('Component Tests', () => {

    describe('ShoppingCart Management Component', () => {
        let comp: ShoppingCartComponent;
        let fixture: ComponentFixture<ShoppingCartComponent>;
        let service: ShoppingCartService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [ShoppingCartComponent],
                providers: [
                    ShoppingCartService
                ]
            })
            .overrideTemplate(ShoppingCartComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingCartComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingCartService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ShoppingCart(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.shoppingCarts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

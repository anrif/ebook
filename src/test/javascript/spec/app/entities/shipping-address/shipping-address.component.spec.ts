/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ArticleTestModule } from '../../../test.module';
import { ShippingAddressComponent } from '../../../../../../main/webapp/app/entities/shipping-address/shipping-address.component';
import { ShippingAddressService } from '../../../../../../main/webapp/app/entities/shipping-address/shipping-address.service';
import { ShippingAddress } from '../../../../../../main/webapp/app/entities/shipping-address/shipping-address.model';

describe('Component Tests', () => {

    describe('ShippingAddress Management Component', () => {
        let comp: ShippingAddressComponent;
        let fixture: ComponentFixture<ShippingAddressComponent>;
        let service: ShippingAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [ShippingAddressComponent],
                providers: [
                    ShippingAddressService
                ]
            })
            .overrideTemplate(ShippingAddressComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShippingAddressComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShippingAddressService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ShippingAddress(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.shippingAddresses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

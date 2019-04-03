/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { ShippingAddressDetailComponent } from '../../../../../../main/webapp/app/entities/shipping-address/shipping-address-detail.component';
import { ShippingAddressService } from '../../../../../../main/webapp/app/entities/shipping-address/shipping-address.service';
import { ShippingAddress } from '../../../../../../main/webapp/app/entities/shipping-address/shipping-address.model';

describe('Component Tests', () => {

    describe('ShippingAddress Management Detail Component', () => {
        let comp: ShippingAddressDetailComponent;
        let fixture: ComponentFixture<ShippingAddressDetailComponent>;
        let service: ShippingAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [ShippingAddressDetailComponent],
                providers: [
                    ShippingAddressService
                ]
            })
            .overrideTemplate(ShippingAddressDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShippingAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShippingAddressService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ShippingAddress(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.shippingAddress).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

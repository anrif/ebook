/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { BillingAddressDetailComponent } from '../../../../../../main/webapp/app/entities/billing-address/billing-address-detail.component';
import { BillingAddressService } from '../../../../../../main/webapp/app/entities/billing-address/billing-address.service';
import { BillingAddress } from '../../../../../../main/webapp/app/entities/billing-address/billing-address.model';

describe('Component Tests', () => {

    describe('BillingAddress Management Detail Component', () => {
        let comp: BillingAddressDetailComponent;
        let fixture: ComponentFixture<BillingAddressDetailComponent>;
        let service: BillingAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [BillingAddressDetailComponent],
                providers: [
                    BillingAddressService
                ]
            })
            .overrideTemplate(BillingAddressDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BillingAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingAddressService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new BillingAddress(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.billingAddress).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

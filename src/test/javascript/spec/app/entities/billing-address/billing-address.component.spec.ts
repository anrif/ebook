/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ArticleTestModule } from '../../../test.module';
import { BillingAddressComponent } from '../../../../../../main/webapp/app/entities/billing-address/billing-address.component';
import { BillingAddressService } from '../../../../../../main/webapp/app/entities/billing-address/billing-address.service';
import { BillingAddress } from '../../../../../../main/webapp/app/entities/billing-address/billing-address.model';

describe('Component Tests', () => {

    describe('BillingAddress Management Component', () => {
        let comp: BillingAddressComponent;
        let fixture: ComponentFixture<BillingAddressComponent>;
        let service: BillingAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [BillingAddressComponent],
                providers: [
                    BillingAddressService
                ]
            })
            .overrideTemplate(BillingAddressComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BillingAddressComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingAddressService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new BillingAddress(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.billingAddresses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

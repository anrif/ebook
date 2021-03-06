/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { PaymentDetailComponent } from '../../../../../../main/webapp/app/entities/payment/payment-detail.component';
import { PaymentService } from '../../../../../../main/webapp/app/entities/payment/payment.service';
import { Payment } from '../../../../../../main/webapp/app/entities/payment/payment.model';

describe('Component Tests', () => {

    describe('Payment Management Detail Component', () => {
        let comp: PaymentDetailComponent;
        let fixture: ComponentFixture<PaymentDetailComponent>;
        let service: PaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [PaymentDetailComponent],
                providers: [
                    PaymentService
                ]
            })
            .overrideTemplate(PaymentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Payment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.payment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { UserPaymentDetailComponent } from '../../../../../../main/webapp/app/entities/user-payment/user-payment-detail.component';
import { UserPaymentService } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.service';
import { UserPayment } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.model';

describe('Component Tests', () => {

    describe('UserPayment Management Detail Component', () => {
        let comp: UserPaymentDetailComponent;
        let fixture: ComponentFixture<UserPaymentDetailComponent>;
        let service: UserPaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserPaymentDetailComponent],
                providers: [
                    UserPaymentService
                ]
            })
            .overrideTemplate(UserPaymentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPaymentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new UserPayment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userPayment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

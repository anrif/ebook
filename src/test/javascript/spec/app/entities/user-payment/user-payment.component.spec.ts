/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ArticleTestModule } from '../../../test.module';
import { UserPaymentComponent } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.component';
import { UserPaymentService } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.service';
import { UserPayment } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.model';

describe('Component Tests', () => {

    describe('UserPayment Management Component', () => {
        let comp: UserPaymentComponent;
        let fixture: ComponentFixture<UserPaymentComponent>;
        let service: UserPaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserPaymentComponent],
                providers: [
                    UserPaymentService
                ]
            })
            .overrideTemplate(UserPaymentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPaymentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new UserPayment(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userPayments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { UserBillingDetailComponent } from '../../../../../../main/webapp/app/entities/user-billing/user-billing-detail.component';
import { UserBillingService } from '../../../../../../main/webapp/app/entities/user-billing/user-billing.service';
import { UserBilling } from '../../../../../../main/webapp/app/entities/user-billing/user-billing.model';

describe('Component Tests', () => {

    describe('UserBilling Management Detail Component', () => {
        let comp: UserBillingDetailComponent;
        let fixture: ComponentFixture<UserBillingDetailComponent>;
        let service: UserBillingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserBillingDetailComponent],
                providers: [
                    UserBillingService
                ]
            })
            .overrideTemplate(UserBillingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserBillingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserBillingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new UserBilling(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userBilling).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

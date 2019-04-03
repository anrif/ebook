/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ArticleTestModule } from '../../../test.module';
import { UserBillingComponent } from '../../../../../../main/webapp/app/entities/user-billing/user-billing.component';
import { UserBillingService } from '../../../../../../main/webapp/app/entities/user-billing/user-billing.service';
import { UserBilling } from '../../../../../../main/webapp/app/entities/user-billing/user-billing.model';

describe('Component Tests', () => {

    describe('UserBilling Management Component', () => {
        let comp: UserBillingComponent;
        let fixture: ComponentFixture<UserBillingComponent>;
        let service: UserBillingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserBillingComponent],
                providers: [
                    UserBillingService
                ]
            })
            .overrideTemplate(UserBillingComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserBillingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserBillingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new UserBilling(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userBillings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

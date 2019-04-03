/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { UserShippingDetailComponent } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping-detail.component';
import { UserShippingService } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping.service';
import { UserShipping } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping.model';

describe('Component Tests', () => {

    describe('UserShipping Management Detail Component', () => {
        let comp: UserShippingDetailComponent;
        let fixture: ComponentFixture<UserShippingDetailComponent>;
        let service: UserShippingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserShippingDetailComponent],
                providers: [
                    UserShippingService
                ]
            })
            .overrideTemplate(UserShippingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserShippingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserShippingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new UserShipping(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userShipping).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

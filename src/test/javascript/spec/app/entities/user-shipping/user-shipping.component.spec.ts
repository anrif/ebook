/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ArticleTestModule } from '../../../test.module';
import { UserShippingComponent } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping.component';
import { UserShippingService } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping.service';
import { UserShipping } from '../../../../../../main/webapp/app/entities/user-shipping/user-shipping.model';

describe('Component Tests', () => {

    describe('UserShipping Management Component', () => {
        let comp: UserShippingComponent;
        let fixture: ComponentFixture<UserShippingComponent>;
        let service: UserShippingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [UserShippingComponent],
                providers: [
                    UserShippingService
                ]
            })
            .overrideTemplate(UserShippingComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserShippingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserShippingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new UserShipping(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userShippings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

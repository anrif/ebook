/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ArticleTestModule } from '../../../test.module';
import { OrderedDetailComponent } from '../../../../../../main/webapp/app/entities/ordered/ordered-detail.component';
import { OrderedService } from '../../../../../../main/webapp/app/entities/ordered/ordered.service';
import { Ordered } from '../../../../../../main/webapp/app/entities/ordered/ordered.model';

describe('Component Tests', () => {

    describe('Ordered Management Detail Component', () => {
        let comp: OrderedDetailComponent;
        let fixture: ComponentFixture<OrderedDetailComponent>;
        let service: OrderedService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [OrderedDetailComponent],
                providers: [
                    OrderedService
                ]
            })
            .overrideTemplate(OrderedDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderedDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderedService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Ordered(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ordered).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

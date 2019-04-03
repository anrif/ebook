/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ArticleTestModule } from '../../../test.module';
import { OrderedComponent } from '../../../../../../main/webapp/app/entities/ordered/ordered.component';
import { OrderedService } from '../../../../../../main/webapp/app/entities/ordered/ordered.service';
import { Ordered } from '../../../../../../main/webapp/app/entities/ordered/ordered.model';

describe('Component Tests', () => {

    describe('Ordered Management Component', () => {
        let comp: OrderedComponent;
        let fixture: ComponentFixture<OrderedComponent>;
        let service: OrderedService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [OrderedComponent],
                providers: [
                    OrderedService
                ]
            })
            .overrideTemplate(OrderedComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderedComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderedService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Ordered(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ordereds[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

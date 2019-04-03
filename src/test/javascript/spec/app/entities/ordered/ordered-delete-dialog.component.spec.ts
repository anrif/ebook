/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleTestModule } from '../../../test.module';
import { OrderedDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/ordered/ordered-delete-dialog.component';
import { OrderedService } from '../../../../../../main/webapp/app/entities/ordered/ordered.service';

describe('Component Tests', () => {

    describe('Ordered Management Delete Component', () => {
        let comp: OrderedDeleteDialogComponent;
        let fixture: ComponentFixture<OrderedDeleteDialogComponent>;
        let service: OrderedService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArticleTestModule],
                declarations: [OrderedDeleteDialogComponent],
                providers: [
                    OrderedService
                ]
            })
            .overrideTemplate(OrderedDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderedDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderedService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

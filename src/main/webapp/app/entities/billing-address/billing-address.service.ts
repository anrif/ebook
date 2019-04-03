import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BillingAddress } from './billing-address.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BillingAddressService {

    private resourceUrl =  SERVER_API_URL + 'api/billing-addresses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/billing-addresses';

    constructor(private http: Http) { }

    create(billingAddress: BillingAddress): Observable<BillingAddress> {
        const copy = this.convert(billingAddress);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(billingAddress: BillingAddress): Observable<BillingAddress> {
        const copy = this.convert(billingAddress);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<BillingAddress> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to BillingAddress.
     */
    private convertItemFromServer(json: any): BillingAddress {
        const entity: BillingAddress = Object.assign(new BillingAddress(), json);
        return entity;
    }

    /**
     * Convert a BillingAddress to a JSON which can be sent to the server.
     */
    private convert(billingAddress: BillingAddress): BillingAddress {
        const copy: BillingAddress = Object.assign({}, billingAddress);
        return copy;
    }
}

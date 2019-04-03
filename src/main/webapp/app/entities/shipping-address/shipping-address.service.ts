import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ShippingAddress } from './shipping-address.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ShippingAddressService {

    private resourceUrl =  SERVER_API_URL + 'api/shipping-addresses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/shipping-addresses';

    constructor(private http: Http) { }

    create(shippingAddress: ShippingAddress): Observable<ShippingAddress> {
        const copy = this.convert(shippingAddress);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(shippingAddress: ShippingAddress): Observable<ShippingAddress> {
        const copy = this.convert(shippingAddress);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ShippingAddress> {
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
     * Convert a returned JSON object to ShippingAddress.
     */
    private convertItemFromServer(json: any): ShippingAddress {
        const entity: ShippingAddress = Object.assign(new ShippingAddress(), json);
        return entity;
    }

    /**
     * Convert a ShippingAddress to a JSON which can be sent to the server.
     */
    private convert(shippingAddress: ShippingAddress): ShippingAddress {
        const copy: ShippingAddress = Object.assign({}, shippingAddress);
        return copy;
    }
}

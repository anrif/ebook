import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserShipping } from './user-shipping.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserShippingService {

    private resourceUrl =  SERVER_API_URL + 'api/user-shippings';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/user-shippings';

    constructor(private http: Http) { }

    create(userShipping: UserShipping): Observable<UserShipping> {
        const copy = this.convert(userShipping);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(userShipping: UserShipping): Observable<UserShipping> {
        const copy = this.convert(userShipping);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<UserShipping> {
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
     * Convert a returned JSON object to UserShipping.
     */
    private convertItemFromServer(json: any): UserShipping {
        const entity: UserShipping = Object.assign(new UserShipping(), json);
        return entity;
    }

    /**
     * Convert a UserShipping to a JSON which can be sent to the server.
     */
    private convert(userShipping: UserShipping): UserShipping {
        const copy: UserShipping = Object.assign({}, userShipping);
        return copy;
    }
}

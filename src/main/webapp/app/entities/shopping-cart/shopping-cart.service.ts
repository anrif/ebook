import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ShoppingCart } from './shopping-cart.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ShoppingCartService {

    private resourceUrl =  SERVER_API_URL + 'api/shopping-carts';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/shopping-carts';

    constructor(private http: Http) { }

    create(shoppingCart: ShoppingCart): Observable<ShoppingCart> {
        const copy = this.convert(shoppingCart);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(shoppingCart: ShoppingCart): Observable<ShoppingCart> {
        const copy = this.convert(shoppingCart);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ShoppingCart> {
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
     * Convert a returned JSON object to ShoppingCart.
     */
    private convertItemFromServer(json: any): ShoppingCart {
        const entity: ShoppingCart = Object.assign(new ShoppingCart(), json);
        return entity;
    }

    /**
     * Convert a ShoppingCart to a JSON which can be sent to the server.
     */
    private convert(shoppingCart: ShoppingCart): ShoppingCart {
        const copy: ShoppingCart = Object.assign({}, shoppingCart);
        return copy;
    }
}

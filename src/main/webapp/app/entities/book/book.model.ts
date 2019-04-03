import { BaseEntity } from './../../shared';

export const enum Language {
    'FRENCH',
    'ENGLISH'
}

export const enum BookCategory {
    'MANAGEMENT',
    'FICTION',
    'ENGINEERING',
    'PROGRAMMING',
    'ARTS_LITERATURE'
}

export const enum BookFormat {
    'PAPERBACK',
    'HARDCOVER'
}

export class Book implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public author?: string,
        public publisher?: string,
        public publicationDate?: string,
        public language?: Language,
        public category?: BookCategory,
        public numberOfPages?: number,
        public format?: BookFormat,
        public isbn?: number,
        public shippingWeight?: number,
        public lastPrice?: number,
        public ourPrice?: number,
        public active?: boolean,
        public description?: any,
        public inStockNumber?: number,
        public bookImageContentType?: string,
        public bookImage?: any,
    ) {
        this.active = false;
    }
}

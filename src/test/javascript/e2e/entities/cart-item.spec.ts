import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('CartItem e2e test', () => {

    let navBarPage: NavBarPage;
    let cartItemDialogPage: CartItemDialogPage;
    let cartItemComponentsPage: CartItemComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CartItems', () => {
        navBarPage.goToEntity('cart-item');
        cartItemComponentsPage = new CartItemComponentsPage();
        expect(cartItemComponentsPage.getTitle())
            .toMatch(/articleApp.cartItem.home.title/);

    });

    it('should load create CartItem dialog', () => {
        cartItemComponentsPage.clickOnCreateButton();
        cartItemDialogPage = new CartItemDialogPage();
        expect(cartItemDialogPage.getModalTitle())
            .toMatch(/articleApp.cartItem.home.createOrEditLabel/);
        cartItemDialogPage.close();
    });

    it('should create and save CartItems', () => {
        cartItemComponentsPage.clickOnCreateButton();
        cartItemDialogPage.setQtyInput('5');
        expect(cartItemDialogPage.getQtyInput()).toMatch('5');
        cartItemDialogPage.setSubtotalInput('5');
        expect(cartItemDialogPage.getSubtotalInput()).toMatch('5');
        cartItemDialogPage.bookSelectLastOption();
        cartItemDialogPage.shoppingCartSelectLastOption();
        cartItemDialogPage.orderedSelectLastOption();
        cartItemDialogPage.save();
        expect(cartItemDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CartItemComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-cart-item div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CartItemDialogPage {
    modalTitle = element(by.css('h4#myCartItemLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    qtyInput = element(by.css('input#field_qty'));
    subtotalInput = element(by.css('input#field_subtotal'));
    bookSelect = element(by.css('select#field_book'));
    shoppingCartSelect = element(by.css('select#field_shoppingCart'));
    orderedSelect = element(by.css('select#field_ordered'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setQtyInput = function(qty) {
        this.qtyInput.sendKeys(qty);
    }

    getQtyInput = function() {
        return this.qtyInput.getAttribute('value');
    }

    setSubtotalInput = function(subtotal) {
        this.subtotalInput.sendKeys(subtotal);
    }

    getSubtotalInput = function() {
        return this.subtotalInput.getAttribute('value');
    }

    bookSelectLastOption = function() {
        this.bookSelect.all(by.tagName('option')).last().click();
    }

    bookSelectOption = function(option) {
        this.bookSelect.sendKeys(option);
    }

    getBookSelect = function() {
        return this.bookSelect;
    }

    getBookSelectedOption = function() {
        return this.bookSelect.element(by.css('option:checked')).getText();
    }

    shoppingCartSelectLastOption = function() {
        this.shoppingCartSelect.all(by.tagName('option')).last().click();
    }

    shoppingCartSelectOption = function(option) {
        this.shoppingCartSelect.sendKeys(option);
    }

    getShoppingCartSelect = function() {
        return this.shoppingCartSelect;
    }

    getShoppingCartSelectedOption = function() {
        return this.shoppingCartSelect.element(by.css('option:checked')).getText();
    }

    orderedSelectLastOption = function() {
        this.orderedSelect.all(by.tagName('option')).last().click();
    }

    orderedSelectOption = function(option) {
        this.orderedSelect.sendKeys(option);
    }

    getOrderedSelect = function() {
        return this.orderedSelect;
    }

    getOrderedSelectedOption = function() {
        return this.orderedSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}

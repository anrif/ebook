import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ShoppingCart e2e test', () => {

    let navBarPage: NavBarPage;
    let shoppingCartDialogPage: ShoppingCartDialogPage;
    let shoppingCartComponentsPage: ShoppingCartComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ShoppingCarts', () => {
        navBarPage.goToEntity('shopping-cart');
        shoppingCartComponentsPage = new ShoppingCartComponentsPage();
        expect(shoppingCartComponentsPage.getTitle())
            .toMatch(/articleApp.shoppingCart.home.title/);

    });

    it('should load create ShoppingCart dialog', () => {
        shoppingCartComponentsPage.clickOnCreateButton();
        shoppingCartDialogPage = new ShoppingCartDialogPage();
        expect(shoppingCartDialogPage.getModalTitle())
            .toMatch(/articleApp.shoppingCart.home.createOrEditLabel/);
        shoppingCartDialogPage.close();
    });

    it('should create and save ShoppingCarts', () => {
        shoppingCartComponentsPage.clickOnCreateButton();
        shoppingCartDialogPage.setGrandTotalInput('5');
        expect(shoppingCartDialogPage.getGrandTotalInput()).toMatch('5');
        shoppingCartDialogPage.userSelectLastOption();
        shoppingCartDialogPage.save();
        expect(shoppingCartDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ShoppingCartComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-shopping-cart div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ShoppingCartDialogPage {
    modalTitle = element(by.css('h4#myShoppingCartLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    grandTotalInput = element(by.css('input#field_grandTotal'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setGrandTotalInput = function(grandTotal) {
        this.grandTotalInput.sendKeys(grandTotal);
    }

    getGrandTotalInput = function() {
        return this.grandTotalInput.getAttribute('value');
    }

    userSelectLastOption = function() {
        this.userSelect.all(by.tagName('option')).last().click();
    }

    userSelectOption = function(option) {
        this.userSelect.sendKeys(option);
    }

    getUserSelect = function() {
        return this.userSelect;
    }

    getUserSelectedOption = function() {
        return this.userSelect.element(by.css('option:checked')).getText();
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

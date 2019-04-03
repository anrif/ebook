import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Ordered e2e test', () => {

    let navBarPage: NavBarPage;
    let orderedDialogPage: OrderedDialogPage;
    let orderedComponentsPage: OrderedComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ordereds', () => {
        navBarPage.goToEntity('ordered');
        orderedComponentsPage = new OrderedComponentsPage();
        expect(orderedComponentsPage.getTitle())
            .toMatch(/articleApp.ordered.home.title/);

    });

    it('should load create Ordered dialog', () => {
        orderedComponentsPage.clickOnCreateButton();
        orderedDialogPage = new OrderedDialogPage();
        expect(orderedDialogPage.getModalTitle())
            .toMatch(/articleApp.ordered.home.createOrEditLabel/);
        orderedDialogPage.close();
    });

    it('should create and save Ordereds', () => {
        orderedComponentsPage.clickOnCreateButton();
        orderedDialogPage.setOrderDateInput('2000-12-31');
        expect(orderedDialogPage.getOrderDateInput()).toMatch('2000-12-31');
        orderedDialogPage.setShippingDateInput('2000-12-31');
        expect(orderedDialogPage.getShippingDateInput()).toMatch('2000-12-31');
        orderedDialogPage.setShippingMethodInput('shippingMethod');
        expect(orderedDialogPage.getShippingMethodInput()).toMatch('shippingMethod');
        orderedDialogPage.setOrderStatusInput('orderStatus');
        expect(orderedDialogPage.getOrderStatusInput()).toMatch('orderStatus');
        orderedDialogPage.setOrderTotalInput('5');
        expect(orderedDialogPage.getOrderTotalInput()).toMatch('5');
        // orderedDialogPage.shippingAddresssSelectLastOption();
        // orderedDialogPage.billingAddresssSelectLastOption();
        // orderedDialogPage.paymentSelectLastOption();
        orderedDialogPage.save();
        expect(orderedDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OrderedComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ordered div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OrderedDialogPage {
    modalTitle = element(by.css('h4#myOrderedLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    orderDateInput = element(by.css('input#field_orderDate'));
    shippingDateInput = element(by.css('input#field_shippingDate'));
    shippingMethodInput = element(by.css('input#field_shippingMethod'));
    orderStatusInput = element(by.css('input#field_orderStatus'));
    orderTotalInput = element(by.css('input#field_orderTotal'));
    shippingAddresssSelect = element(by.css('select#field_shippingAddresss'));
    billingAddresssSelect = element(by.css('select#field_billingAddresss'));
    paymentSelect = element(by.css('select#field_payment'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setOrderDateInput = function(orderDate) {
        this.orderDateInput.sendKeys(orderDate);
    }

    getOrderDateInput = function() {
        return this.orderDateInput.getAttribute('value');
    }

    setShippingDateInput = function(shippingDate) {
        this.shippingDateInput.sendKeys(shippingDate);
    }

    getShippingDateInput = function() {
        return this.shippingDateInput.getAttribute('value');
    }

    setShippingMethodInput = function(shippingMethod) {
        this.shippingMethodInput.sendKeys(shippingMethod);
    }

    getShippingMethodInput = function() {
        return this.shippingMethodInput.getAttribute('value');
    }

    setOrderStatusInput = function(orderStatus) {
        this.orderStatusInput.sendKeys(orderStatus);
    }

    getOrderStatusInput = function() {
        return this.orderStatusInput.getAttribute('value');
    }

    setOrderTotalInput = function(orderTotal) {
        this.orderTotalInput.sendKeys(orderTotal);
    }

    getOrderTotalInput = function() {
        return this.orderTotalInput.getAttribute('value');
    }

    shippingAddresssSelectLastOption = function() {
        this.shippingAddresssSelect.all(by.tagName('option')).last().click();
    }

    shippingAddresssSelectOption = function(option) {
        this.shippingAddresssSelect.sendKeys(option);
    }

    getShippingAddresssSelect = function() {
        return this.shippingAddresssSelect;
    }

    getShippingAddresssSelectedOption = function() {
        return this.shippingAddresssSelect.element(by.css('option:checked')).getText();
    }

    billingAddresssSelectLastOption = function() {
        this.billingAddresssSelect.all(by.tagName('option')).last().click();
    }

    billingAddresssSelectOption = function(option) {
        this.billingAddresssSelect.sendKeys(option);
    }

    getBillingAddresssSelect = function() {
        return this.billingAddresssSelect;
    }

    getBillingAddresssSelectedOption = function() {
        return this.billingAddresssSelect.element(by.css('option:checked')).getText();
    }

    paymentSelectLastOption = function() {
        this.paymentSelect.all(by.tagName('option')).last().click();
    }

    paymentSelectOption = function(option) {
        this.paymentSelect.sendKeys(option);
    }

    getPaymentSelect = function() {
        return this.paymentSelect;
    }

    getPaymentSelectedOption = function() {
        return this.paymentSelect.element(by.css('option:checked')).getText();
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

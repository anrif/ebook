import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ShippingAddress e2e test', () => {

    let navBarPage: NavBarPage;
    let shippingAddressDialogPage: ShippingAddressDialogPage;
    let shippingAddressComponentsPage: ShippingAddressComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ShippingAddresses', () => {
        navBarPage.goToEntity('shipping-address');
        shippingAddressComponentsPage = new ShippingAddressComponentsPage();
        expect(shippingAddressComponentsPage.getTitle())
            .toMatch(/articleApp.shippingAddress.home.title/);

    });

    it('should load create ShippingAddress dialog', () => {
        shippingAddressComponentsPage.clickOnCreateButton();
        shippingAddressDialogPage = new ShippingAddressDialogPage();
        expect(shippingAddressDialogPage.getModalTitle())
            .toMatch(/articleApp.shippingAddress.home.createOrEditLabel/);
        shippingAddressDialogPage.close();
    });

    it('should create and save ShippingAddresses', () => {
        shippingAddressComponentsPage.clickOnCreateButton();
        shippingAddressDialogPage.setShippingAddressNameInput('shippingAddressName');
        expect(shippingAddressDialogPage.getShippingAddressNameInput()).toMatch('shippingAddressName');
        shippingAddressDialogPage.setShippingAddressStreet1Input('shippingAddressStreet1');
        expect(shippingAddressDialogPage.getShippingAddressStreet1Input()).toMatch('shippingAddressStreet1');
        shippingAddressDialogPage.setShippingAddressStreet2Input('shippingAddressStreet2');
        expect(shippingAddressDialogPage.getShippingAddressStreet2Input()).toMatch('shippingAddressStreet2');
        shippingAddressDialogPage.setShippingAddressCityInput('shippingAddressCity');
        expect(shippingAddressDialogPage.getShippingAddressCityInput()).toMatch('shippingAddressCity');
        shippingAddressDialogPage.setShippingAddressStateInput('shippingAddressState');
        expect(shippingAddressDialogPage.getShippingAddressStateInput()).toMatch('shippingAddressState');
        shippingAddressDialogPage.setShippingAddressCountryInput('shippingAddressCountry');
        expect(shippingAddressDialogPage.getShippingAddressCountryInput()).toMatch('shippingAddressCountry');
        shippingAddressDialogPage.setShippingAddressZipcodeInput('shippingAddressZipcode');
        expect(shippingAddressDialogPage.getShippingAddressZipcodeInput()).toMatch('shippingAddressZipcode');
        shippingAddressDialogPage.save();
        expect(shippingAddressDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ShippingAddressComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-shipping-address div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ShippingAddressDialogPage {
    modalTitle = element(by.css('h4#myShippingAddressLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    shippingAddressNameInput = element(by.css('input#field_shippingAddressName'));
    shippingAddressStreet1Input = element(by.css('input#field_shippingAddressStreet1'));
    shippingAddressStreet2Input = element(by.css('input#field_shippingAddressStreet2'));
    shippingAddressCityInput = element(by.css('input#field_shippingAddressCity'));
    shippingAddressStateInput = element(by.css('input#field_shippingAddressState'));
    shippingAddressCountryInput = element(by.css('input#field_shippingAddressCountry'));
    shippingAddressZipcodeInput = element(by.css('input#field_shippingAddressZipcode'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setShippingAddressNameInput = function(shippingAddressName) {
        this.shippingAddressNameInput.sendKeys(shippingAddressName);
    }

    getShippingAddressNameInput = function() {
        return this.shippingAddressNameInput.getAttribute('value');
    }

    setShippingAddressStreet1Input = function(shippingAddressStreet1) {
        this.shippingAddressStreet1Input.sendKeys(shippingAddressStreet1);
    }

    getShippingAddressStreet1Input = function() {
        return this.shippingAddressStreet1Input.getAttribute('value');
    }

    setShippingAddressStreet2Input = function(shippingAddressStreet2) {
        this.shippingAddressStreet2Input.sendKeys(shippingAddressStreet2);
    }

    getShippingAddressStreet2Input = function() {
        return this.shippingAddressStreet2Input.getAttribute('value');
    }

    setShippingAddressCityInput = function(shippingAddressCity) {
        this.shippingAddressCityInput.sendKeys(shippingAddressCity);
    }

    getShippingAddressCityInput = function() {
        return this.shippingAddressCityInput.getAttribute('value');
    }

    setShippingAddressStateInput = function(shippingAddressState) {
        this.shippingAddressStateInput.sendKeys(shippingAddressState);
    }

    getShippingAddressStateInput = function() {
        return this.shippingAddressStateInput.getAttribute('value');
    }

    setShippingAddressCountryInput = function(shippingAddressCountry) {
        this.shippingAddressCountryInput.sendKeys(shippingAddressCountry);
    }

    getShippingAddressCountryInput = function() {
        return this.shippingAddressCountryInput.getAttribute('value');
    }

    setShippingAddressZipcodeInput = function(shippingAddressZipcode) {
        this.shippingAddressZipcodeInput.sendKeys(shippingAddressZipcode);
    }

    getShippingAddressZipcodeInput = function() {
        return this.shippingAddressZipcodeInput.getAttribute('value');
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

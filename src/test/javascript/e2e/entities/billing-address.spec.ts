import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('BillingAddress e2e test', () => {

    let navBarPage: NavBarPage;
    let billingAddressDialogPage: BillingAddressDialogPage;
    let billingAddressComponentsPage: BillingAddressComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BillingAddresses', () => {
        navBarPage.goToEntity('billing-address');
        billingAddressComponentsPage = new BillingAddressComponentsPage();
        expect(billingAddressComponentsPage.getTitle())
            .toMatch(/articleApp.billingAddress.home.title/);

    });

    it('should load create BillingAddress dialog', () => {
        billingAddressComponentsPage.clickOnCreateButton();
        billingAddressDialogPage = new BillingAddressDialogPage();
        expect(billingAddressDialogPage.getModalTitle())
            .toMatch(/articleApp.billingAddress.home.createOrEditLabel/);
        billingAddressDialogPage.close();
    });

    it('should create and save BillingAddresses', () => {
        billingAddressComponentsPage.clickOnCreateButton();
        billingAddressDialogPage.setBillingAddressNameInput('billingAddressName');
        expect(billingAddressDialogPage.getBillingAddressNameInput()).toMatch('billingAddressName');
        billingAddressDialogPage.setBillingAddressStreet1Input('billingAddressStreet1');
        expect(billingAddressDialogPage.getBillingAddressStreet1Input()).toMatch('billingAddressStreet1');
        billingAddressDialogPage.setBillingAddressStreet2Input('billingAddressStreet2');
        expect(billingAddressDialogPage.getBillingAddressStreet2Input()).toMatch('billingAddressStreet2');
        billingAddressDialogPage.setBillingAddressCityInput('billingAddressCity');
        expect(billingAddressDialogPage.getBillingAddressCityInput()).toMatch('billingAddressCity');
        billingAddressDialogPage.setBillingAddressStateInput('billingAddressState');
        expect(billingAddressDialogPage.getBillingAddressStateInput()).toMatch('billingAddressState');
        billingAddressDialogPage.setBillingAddressCountryInput('billingAddressCountry');
        expect(billingAddressDialogPage.getBillingAddressCountryInput()).toMatch('billingAddressCountry');
        billingAddressDialogPage.setBillingAddressZipcodeInput('billingAddressZipcode');
        expect(billingAddressDialogPage.getBillingAddressZipcodeInput()).toMatch('billingAddressZipcode');
        billingAddressDialogPage.save();
        expect(billingAddressDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BillingAddressComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-billing-address div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BillingAddressDialogPage {
    modalTitle = element(by.css('h4#myBillingAddressLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    billingAddressNameInput = element(by.css('input#field_billingAddressName'));
    billingAddressStreet1Input = element(by.css('input#field_billingAddressStreet1'));
    billingAddressStreet2Input = element(by.css('input#field_billingAddressStreet2'));
    billingAddressCityInput = element(by.css('input#field_billingAddressCity'));
    billingAddressStateInput = element(by.css('input#field_billingAddressState'));
    billingAddressCountryInput = element(by.css('input#field_billingAddressCountry'));
    billingAddressZipcodeInput = element(by.css('input#field_billingAddressZipcode'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setBillingAddressNameInput = function(billingAddressName) {
        this.billingAddressNameInput.sendKeys(billingAddressName);
    }

    getBillingAddressNameInput = function() {
        return this.billingAddressNameInput.getAttribute('value');
    }

    setBillingAddressStreet1Input = function(billingAddressStreet1) {
        this.billingAddressStreet1Input.sendKeys(billingAddressStreet1);
    }

    getBillingAddressStreet1Input = function() {
        return this.billingAddressStreet1Input.getAttribute('value');
    }

    setBillingAddressStreet2Input = function(billingAddressStreet2) {
        this.billingAddressStreet2Input.sendKeys(billingAddressStreet2);
    }

    getBillingAddressStreet2Input = function() {
        return this.billingAddressStreet2Input.getAttribute('value');
    }

    setBillingAddressCityInput = function(billingAddressCity) {
        this.billingAddressCityInput.sendKeys(billingAddressCity);
    }

    getBillingAddressCityInput = function() {
        return this.billingAddressCityInput.getAttribute('value');
    }

    setBillingAddressStateInput = function(billingAddressState) {
        this.billingAddressStateInput.sendKeys(billingAddressState);
    }

    getBillingAddressStateInput = function() {
        return this.billingAddressStateInput.getAttribute('value');
    }

    setBillingAddressCountryInput = function(billingAddressCountry) {
        this.billingAddressCountryInput.sendKeys(billingAddressCountry);
    }

    getBillingAddressCountryInput = function() {
        return this.billingAddressCountryInput.getAttribute('value');
    }

    setBillingAddressZipcodeInput = function(billingAddressZipcode) {
        this.billingAddressZipcodeInput.sendKeys(billingAddressZipcode);
    }

    getBillingAddressZipcodeInput = function() {
        return this.billingAddressZipcodeInput.getAttribute('value');
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

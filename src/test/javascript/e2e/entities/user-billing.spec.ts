import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('UserBilling e2e test', () => {

    let navBarPage: NavBarPage;
    let userBillingDialogPage: UserBillingDialogPage;
    let userBillingComponentsPage: UserBillingComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserBillings', () => {
        navBarPage.goToEntity('user-billing');
        userBillingComponentsPage = new UserBillingComponentsPage();
        expect(userBillingComponentsPage.getTitle())
            .toMatch(/articleApp.userBilling.home.title/);

    });

    it('should load create UserBilling dialog', () => {
        userBillingComponentsPage.clickOnCreateButton();
        userBillingDialogPage = new UserBillingDialogPage();
        expect(userBillingDialogPage.getModalTitle())
            .toMatch(/articleApp.userBilling.home.createOrEditLabel/);
        userBillingDialogPage.close();
    });

    it('should create and save UserBillings', () => {
        userBillingComponentsPage.clickOnCreateButton();
        userBillingDialogPage.setUserBillingNameInput('userBillingName');
        expect(userBillingDialogPage.getUserBillingNameInput()).toMatch('userBillingName');
        userBillingDialogPage.setUserBillingStreet1Input('userBillingStreet1');
        expect(userBillingDialogPage.getUserBillingStreet1Input()).toMatch('userBillingStreet1');
        userBillingDialogPage.setUserBillingStreet2Input('userBillingStreet2');
        expect(userBillingDialogPage.getUserBillingStreet2Input()).toMatch('userBillingStreet2');
        userBillingDialogPage.setUserBillingCityInput('userBillingCity');
        expect(userBillingDialogPage.getUserBillingCityInput()).toMatch('userBillingCity');
        userBillingDialogPage.setUserBillingStateInput('userBillingState');
        expect(userBillingDialogPage.getUserBillingStateInput()).toMatch('userBillingState');
        userBillingDialogPage.setUserBillingCountryInput('userBillingCountry');
        expect(userBillingDialogPage.getUserBillingCountryInput()).toMatch('userBillingCountry');
        userBillingDialogPage.setUserBillingZipcodeInput('userBillingZipcode');
        expect(userBillingDialogPage.getUserBillingZipcodeInput()).toMatch('userBillingZipcode');
        userBillingDialogPage.userSelectLastOption();
        userBillingDialogPage.save();
        expect(userBillingDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserBillingComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-billing div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserBillingDialogPage {
    modalTitle = element(by.css('h4#myUserBillingLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userBillingNameInput = element(by.css('input#field_userBillingName'));
    userBillingStreet1Input = element(by.css('input#field_userBillingStreet1'));
    userBillingStreet2Input = element(by.css('input#field_userBillingStreet2'));
    userBillingCityInput = element(by.css('input#field_userBillingCity'));
    userBillingStateInput = element(by.css('input#field_userBillingState'));
    userBillingCountryInput = element(by.css('input#field_userBillingCountry'));
    userBillingZipcodeInput = element(by.css('input#field_userBillingZipcode'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUserBillingNameInput = function(userBillingName) {
        this.userBillingNameInput.sendKeys(userBillingName);
    }

    getUserBillingNameInput = function() {
        return this.userBillingNameInput.getAttribute('value');
    }

    setUserBillingStreet1Input = function(userBillingStreet1) {
        this.userBillingStreet1Input.sendKeys(userBillingStreet1);
    }

    getUserBillingStreet1Input = function() {
        return this.userBillingStreet1Input.getAttribute('value');
    }

    setUserBillingStreet2Input = function(userBillingStreet2) {
        this.userBillingStreet2Input.sendKeys(userBillingStreet2);
    }

    getUserBillingStreet2Input = function() {
        return this.userBillingStreet2Input.getAttribute('value');
    }

    setUserBillingCityInput = function(userBillingCity) {
        this.userBillingCityInput.sendKeys(userBillingCity);
    }

    getUserBillingCityInput = function() {
        return this.userBillingCityInput.getAttribute('value');
    }

    setUserBillingStateInput = function(userBillingState) {
        this.userBillingStateInput.sendKeys(userBillingState);
    }

    getUserBillingStateInput = function() {
        return this.userBillingStateInput.getAttribute('value');
    }

    setUserBillingCountryInput = function(userBillingCountry) {
        this.userBillingCountryInput.sendKeys(userBillingCountry);
    }

    getUserBillingCountryInput = function() {
        return this.userBillingCountryInput.getAttribute('value');
    }

    setUserBillingZipcodeInput = function(userBillingZipcode) {
        this.userBillingZipcodeInput.sendKeys(userBillingZipcode);
    }

    getUserBillingZipcodeInput = function() {
        return this.userBillingZipcodeInput.getAttribute('value');
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

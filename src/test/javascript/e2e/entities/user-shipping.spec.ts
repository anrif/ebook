import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('UserShipping e2e test', () => {

    let navBarPage: NavBarPage;
    let userShippingDialogPage: UserShippingDialogPage;
    let userShippingComponentsPage: UserShippingComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserShippings', () => {
        navBarPage.goToEntity('user-shipping');
        userShippingComponentsPage = new UserShippingComponentsPage();
        expect(userShippingComponentsPage.getTitle())
            .toMatch(/articleApp.userShipping.home.title/);

    });

    it('should load create UserShipping dialog', () => {
        userShippingComponentsPage.clickOnCreateButton();
        userShippingDialogPage = new UserShippingDialogPage();
        expect(userShippingDialogPage.getModalTitle())
            .toMatch(/articleApp.userShipping.home.createOrEditLabel/);
        userShippingDialogPage.close();
    });

    it('should create and save UserShippings', () => {
        userShippingComponentsPage.clickOnCreateButton();
        userShippingDialogPage.setUserShippingNameInput('userShippingName');
        expect(userShippingDialogPage.getUserShippingNameInput()).toMatch('userShippingName');
        userShippingDialogPage.setUserShippingStreet1Input('userShippingStreet1');
        expect(userShippingDialogPage.getUserShippingStreet1Input()).toMatch('userShippingStreet1');
        userShippingDialogPage.setUserShippingStreet2Input('userShippingStreet2');
        expect(userShippingDialogPage.getUserShippingStreet2Input()).toMatch('userShippingStreet2');
        userShippingDialogPage.setUserShippingCityInput('userShippingCity');
        expect(userShippingDialogPage.getUserShippingCityInput()).toMatch('userShippingCity');
        userShippingDialogPage.setUserShippingStateInput('userShippingState');
        expect(userShippingDialogPage.getUserShippingStateInput()).toMatch('userShippingState');
        userShippingDialogPage.setUserShippingCountryInput('userShippingCountry');
        expect(userShippingDialogPage.getUserShippingCountryInput()).toMatch('userShippingCountry');
        userShippingDialogPage.setUserShippingZipcodeInput('userShippingZipcode');
        expect(userShippingDialogPage.getUserShippingZipcodeInput()).toMatch('userShippingZipcode');
        userShippingDialogPage.getUserShippingDefaultInput().isSelected().then((selected) => {
            if (selected) {
                userShippingDialogPage.getUserShippingDefaultInput().click();
                expect(userShippingDialogPage.getUserShippingDefaultInput().isSelected()).toBeFalsy();
            } else {
                userShippingDialogPage.getUserShippingDefaultInput().click();
                expect(userShippingDialogPage.getUserShippingDefaultInput().isSelected()).toBeTruthy();
            }
        });
        userShippingDialogPage.userSelectLastOption();
        userShippingDialogPage.save();
        expect(userShippingDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserShippingComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-shipping div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserShippingDialogPage {
    modalTitle = element(by.css('h4#myUserShippingLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userShippingNameInput = element(by.css('input#field_userShippingName'));
    userShippingStreet1Input = element(by.css('input#field_userShippingStreet1'));
    userShippingStreet2Input = element(by.css('input#field_userShippingStreet2'));
    userShippingCityInput = element(by.css('input#field_userShippingCity'));
    userShippingStateInput = element(by.css('input#field_userShippingState'));
    userShippingCountryInput = element(by.css('input#field_userShippingCountry'));
    userShippingZipcodeInput = element(by.css('input#field_userShippingZipcode'));
    userShippingDefaultInput = element(by.css('input#field_userShippingDefault'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUserShippingNameInput = function(userShippingName) {
        this.userShippingNameInput.sendKeys(userShippingName);
    }

    getUserShippingNameInput = function() {
        return this.userShippingNameInput.getAttribute('value');
    }

    setUserShippingStreet1Input = function(userShippingStreet1) {
        this.userShippingStreet1Input.sendKeys(userShippingStreet1);
    }

    getUserShippingStreet1Input = function() {
        return this.userShippingStreet1Input.getAttribute('value');
    }

    setUserShippingStreet2Input = function(userShippingStreet2) {
        this.userShippingStreet2Input.sendKeys(userShippingStreet2);
    }

    getUserShippingStreet2Input = function() {
        return this.userShippingStreet2Input.getAttribute('value');
    }

    setUserShippingCityInput = function(userShippingCity) {
        this.userShippingCityInput.sendKeys(userShippingCity);
    }

    getUserShippingCityInput = function() {
        return this.userShippingCityInput.getAttribute('value');
    }

    setUserShippingStateInput = function(userShippingState) {
        this.userShippingStateInput.sendKeys(userShippingState);
    }

    getUserShippingStateInput = function() {
        return this.userShippingStateInput.getAttribute('value');
    }

    setUserShippingCountryInput = function(userShippingCountry) {
        this.userShippingCountryInput.sendKeys(userShippingCountry);
    }

    getUserShippingCountryInput = function() {
        return this.userShippingCountryInput.getAttribute('value');
    }

    setUserShippingZipcodeInput = function(userShippingZipcode) {
        this.userShippingZipcodeInput.sendKeys(userShippingZipcode);
    }

    getUserShippingZipcodeInput = function() {
        return this.userShippingZipcodeInput.getAttribute('value');
    }

    getUserShippingDefaultInput = function() {
        return this.userShippingDefaultInput;
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

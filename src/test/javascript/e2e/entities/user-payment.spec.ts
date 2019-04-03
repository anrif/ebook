import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('UserPayment e2e test', () => {

    let navBarPage: NavBarPage;
    let userPaymentDialogPage: UserPaymentDialogPage;
    let userPaymentComponentsPage: UserPaymentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserPayments', () => {
        navBarPage.goToEntity('user-payment');
        userPaymentComponentsPage = new UserPaymentComponentsPage();
        expect(userPaymentComponentsPage.getTitle())
            .toMatch(/articleApp.userPayment.home.title/);

    });

    it('should load create UserPayment dialog', () => {
        userPaymentComponentsPage.clickOnCreateButton();
        userPaymentDialogPage = new UserPaymentDialogPage();
        expect(userPaymentDialogPage.getModalTitle())
            .toMatch(/articleApp.userPayment.home.createOrEditLabel/);
        userPaymentDialogPage.close();
    });

    it('should create and save UserPayments', () => {
        userPaymentComponentsPage.clickOnCreateButton();
        userPaymentDialogPage.typeSelectLastOption();
        userPaymentDialogPage.setCardNameInput('cardName');
        expect(userPaymentDialogPage.getCardNameInput()).toMatch('cardName');
        userPaymentDialogPage.setCardNumberInput('cardNumber');
        expect(userPaymentDialogPage.getCardNumberInput()).toMatch('cardNumber');
        userPaymentDialogPage.setExpiryMonthInput('5');
        expect(userPaymentDialogPage.getExpiryMonthInput()).toMatch('5');
        userPaymentDialogPage.setExpiryYearInput('5');
        expect(userPaymentDialogPage.getExpiryYearInput()).toMatch('5');
        userPaymentDialogPage.setCvcInput('5');
        expect(userPaymentDialogPage.getCvcInput()).toMatch('5');
        userPaymentDialogPage.setHolderNameInput('holderName');
        expect(userPaymentDialogPage.getHolderNameInput()).toMatch('holderName');
        userPaymentDialogPage.getDefaultPaymentInput().isSelected().then((selected) => {
            if (selected) {
                userPaymentDialogPage.getDefaultPaymentInput().click();
                expect(userPaymentDialogPage.getDefaultPaymentInput().isSelected()).toBeFalsy();
            } else {
                userPaymentDialogPage.getDefaultPaymentInput().click();
                expect(userPaymentDialogPage.getDefaultPaymentInput().isSelected()).toBeTruthy();
            }
        });
        userPaymentDialogPage.userSelectLastOption();
        userPaymentDialogPage.save();
        expect(userPaymentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserPaymentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-payment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserPaymentDialogPage {
    modalTitle = element(by.css('h4#myUserPaymentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    typeSelect = element(by.css('select#field_type'));
    cardNameInput = element(by.css('input#field_cardName'));
    cardNumberInput = element(by.css('input#field_cardNumber'));
    expiryMonthInput = element(by.css('input#field_expiryMonth'));
    expiryYearInput = element(by.css('input#field_expiryYear'));
    cvcInput = element(by.css('input#field_cvc'));
    holderNameInput = element(by.css('input#field_holderName'));
    defaultPaymentInput = element(by.css('input#field_defaultPayment'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTypeSelect = function(type) {
        this.typeSelect.sendKeys(type);
    }

    getTypeSelect = function() {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    typeSelectLastOption = function() {
        this.typeSelect.all(by.tagName('option')).last().click();
    }
    setCardNameInput = function(cardName) {
        this.cardNameInput.sendKeys(cardName);
    }

    getCardNameInput = function() {
        return this.cardNameInput.getAttribute('value');
    }

    setCardNumberInput = function(cardNumber) {
        this.cardNumberInput.sendKeys(cardNumber);
    }

    getCardNumberInput = function() {
        return this.cardNumberInput.getAttribute('value');
    }

    setExpiryMonthInput = function(expiryMonth) {
        this.expiryMonthInput.sendKeys(expiryMonth);
    }

    getExpiryMonthInput = function() {
        return this.expiryMonthInput.getAttribute('value');
    }

    setExpiryYearInput = function(expiryYear) {
        this.expiryYearInput.sendKeys(expiryYear);
    }

    getExpiryYearInput = function() {
        return this.expiryYearInput.getAttribute('value');
    }

    setCvcInput = function(cvc) {
        this.cvcInput.sendKeys(cvc);
    }

    getCvcInput = function() {
        return this.cvcInput.getAttribute('value');
    }

    setHolderNameInput = function(holderName) {
        this.holderNameInput.sendKeys(holderName);
    }

    getHolderNameInput = function() {
        return this.holderNameInput.getAttribute('value');
    }

    getDefaultPaymentInput = function() {
        return this.defaultPaymentInput;
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

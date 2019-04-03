import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Payment e2e test', () => {

    let navBarPage: NavBarPage;
    let paymentDialogPage: PaymentDialogPage;
    let paymentComponentsPage: PaymentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Payments', () => {
        navBarPage.goToEntity('payment');
        paymentComponentsPage = new PaymentComponentsPage();
        expect(paymentComponentsPage.getTitle())
            .toMatch(/articleApp.payment.home.title/);

    });

    it('should load create Payment dialog', () => {
        paymentComponentsPage.clickOnCreateButton();
        paymentDialogPage = new PaymentDialogPage();
        expect(paymentDialogPage.getModalTitle())
            .toMatch(/articleApp.payment.home.createOrEditLabel/);
        paymentDialogPage.close();
    });

    it('should create and save Payments', () => {
        paymentComponentsPage.clickOnCreateButton();
        paymentDialogPage.setTypeInput('type');
        expect(paymentDialogPage.getTypeInput()).toMatch('type');
        paymentDialogPage.setCartNameInput('cartName');
        expect(paymentDialogPage.getCartNameInput()).toMatch('cartName');
        paymentDialogPage.setCardNumberInput('cardNumber');
        expect(paymentDialogPage.getCardNumberInput()).toMatch('cardNumber');
        paymentDialogPage.setExpiryMonthInput('5');
        expect(paymentDialogPage.getExpiryMonthInput()).toMatch('5');
        paymentDialogPage.setExpiryYearInput('5');
        expect(paymentDialogPage.getExpiryYearInput()).toMatch('5');
        paymentDialogPage.setCvcInput('5');
        expect(paymentDialogPage.getCvcInput()).toMatch('5');
        paymentDialogPage.setHolderNameInput('holderName');
        expect(paymentDialogPage.getHolderNameInput()).toMatch('holderName');
        paymentDialogPage.save();
        expect(paymentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PaymentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-payment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PaymentDialogPage {
    modalTitle = element(by.css('h4#myPaymentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    typeInput = element(by.css('input#field_type'));
    cartNameInput = element(by.css('input#field_cartName'));
    cardNumberInput = element(by.css('input#field_cardNumber'));
    expiryMonthInput = element(by.css('input#field_expiryMonth'));
    expiryYearInput = element(by.css('input#field_expiryYear'));
    cvcInput = element(by.css('input#field_cvc'));
    holderNameInput = element(by.css('input#field_holderName'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTypeInput = function(type) {
        this.typeInput.sendKeys(type);
    }

    getTypeInput = function() {
        return this.typeInput.getAttribute('value');
    }

    setCartNameInput = function(cartName) {
        this.cartNameInput.sendKeys(cartName);
    }

    getCartNameInput = function() {
        return this.cartNameInput.getAttribute('value');
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

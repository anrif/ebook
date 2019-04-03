import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Book e2e test', () => {

    let navBarPage: NavBarPage;
    let bookDialogPage: BookDialogPage;
    let bookComponentsPage: BookComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Books', () => {
        navBarPage.goToEntity('book');
        bookComponentsPage = new BookComponentsPage();
        expect(bookComponentsPage.getTitle())
            .toMatch(/articleApp.book.home.title/);

    });

    it('should load create Book dialog', () => {
        bookComponentsPage.clickOnCreateButton();
        bookDialogPage = new BookDialogPage();
        expect(bookDialogPage.getModalTitle())
            .toMatch(/articleApp.book.home.createOrEditLabel/);
        bookDialogPage.close();
    });

    it('should create and save Books', () => {
        bookComponentsPage.clickOnCreateButton();
        bookDialogPage.setTitleInput('title');
        expect(bookDialogPage.getTitleInput()).toMatch('title');
        bookDialogPage.setAuthorInput('author');
        expect(bookDialogPage.getAuthorInput()).toMatch('author');
        bookDialogPage.setPublisherInput('publisher');
        expect(bookDialogPage.getPublisherInput()).toMatch('publisher');
        bookDialogPage.setPublicationDateInput('publicationDate');
        expect(bookDialogPage.getPublicationDateInput()).toMatch('publicationDate');
        bookDialogPage.languageSelectLastOption();
        bookDialogPage.categorySelectLastOption();
        bookDialogPage.setNumberOfPagesInput('5');
        expect(bookDialogPage.getNumberOfPagesInput()).toMatch('5');
        bookDialogPage.formatSelectLastOption();
        bookDialogPage.setIsbnInput('5');
        expect(bookDialogPage.getIsbnInput()).toMatch('5');
        bookDialogPage.setShippingWeightInput('5');
        expect(bookDialogPage.getShippingWeightInput()).toMatch('5');
        bookDialogPage.setLastPriceInput('5');
        expect(bookDialogPage.getLastPriceInput()).toMatch('5');
        bookDialogPage.setOurPriceInput('5');
        expect(bookDialogPage.getOurPriceInput()).toMatch('5');
        bookDialogPage.getActiveInput().isSelected().then((selected) => {
            if (selected) {
                bookDialogPage.getActiveInput().click();
                expect(bookDialogPage.getActiveInput().isSelected()).toBeFalsy();
            } else {
                bookDialogPage.getActiveInput().click();
                expect(bookDialogPage.getActiveInput().isSelected()).toBeTruthy();
            }
        });
        bookDialogPage.setDescriptionInput('description');
        expect(bookDialogPage.getDescriptionInput()).toMatch('description');
        bookDialogPage.setInStockNumberInput('5');
        expect(bookDialogPage.getInStockNumberInput()).toMatch('5');
        bookDialogPage.setBookImageInput(absolutePath);
        bookDialogPage.save();
        expect(bookDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BookComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-book div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BookDialogPage {
    modalTitle = element(by.css('h4#myBookLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    authorInput = element(by.css('input#field_author'));
    publisherInput = element(by.css('input#field_publisher'));
    publicationDateInput = element(by.css('input#field_publicationDate'));
    languageSelect = element(by.css('select#field_language'));
    categorySelect = element(by.css('select#field_category'));
    numberOfPagesInput = element(by.css('input#field_numberOfPages'));
    formatSelect = element(by.css('select#field_format'));
    isbnInput = element(by.css('input#field_isbn'));
    shippingWeightInput = element(by.css('input#field_shippingWeight'));
    lastPriceInput = element(by.css('input#field_lastPrice'));
    ourPriceInput = element(by.css('input#field_ourPrice'));
    activeInput = element(by.css('input#field_active'));
    descriptionInput = element(by.css('textarea#field_description'));
    inStockNumberInput = element(by.css('input#field_inStockNumber'));
    bookImageInput = element(by.css('input#file_bookImage'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    }

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    }

    setAuthorInput = function(author) {
        this.authorInput.sendKeys(author);
    }

    getAuthorInput = function() {
        return this.authorInput.getAttribute('value');
    }

    setPublisherInput = function(publisher) {
        this.publisherInput.sendKeys(publisher);
    }

    getPublisherInput = function() {
        return this.publisherInput.getAttribute('value');
    }

    setPublicationDateInput = function(publicationDate) {
        this.publicationDateInput.sendKeys(publicationDate);
    }

    getPublicationDateInput = function() {
        return this.publicationDateInput.getAttribute('value');
    }

    setLanguageSelect = function(language) {
        this.languageSelect.sendKeys(language);
    }

    getLanguageSelect = function() {
        return this.languageSelect.element(by.css('option:checked')).getText();
    }

    languageSelectLastOption = function() {
        this.languageSelect.all(by.tagName('option')).last().click();
    }
    setCategorySelect = function(category) {
        this.categorySelect.sendKeys(category);
    }

    getCategorySelect = function() {
        return this.categorySelect.element(by.css('option:checked')).getText();
    }

    categorySelectLastOption = function() {
        this.categorySelect.all(by.tagName('option')).last().click();
    }
    setNumberOfPagesInput = function(numberOfPages) {
        this.numberOfPagesInput.sendKeys(numberOfPages);
    }

    getNumberOfPagesInput = function() {
        return this.numberOfPagesInput.getAttribute('value');
    }

    setFormatSelect = function(format) {
        this.formatSelect.sendKeys(format);
    }

    getFormatSelect = function() {
        return this.formatSelect.element(by.css('option:checked')).getText();
    }

    formatSelectLastOption = function() {
        this.formatSelect.all(by.tagName('option')).last().click();
    }
    setIsbnInput = function(isbn) {
        this.isbnInput.sendKeys(isbn);
    }

    getIsbnInput = function() {
        return this.isbnInput.getAttribute('value');
    }

    setShippingWeightInput = function(shippingWeight) {
        this.shippingWeightInput.sendKeys(shippingWeight);
    }

    getShippingWeightInput = function() {
        return this.shippingWeightInput.getAttribute('value');
    }

    setLastPriceInput = function(lastPrice) {
        this.lastPriceInput.sendKeys(lastPrice);
    }

    getLastPriceInput = function() {
        return this.lastPriceInput.getAttribute('value');
    }

    setOurPriceInput = function(ourPrice) {
        this.ourPriceInput.sendKeys(ourPrice);
    }

    getOurPriceInput = function() {
        return this.ourPriceInput.getAttribute('value');
    }

    getActiveInput = function() {
        return this.activeInput;
    }
    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    }

    setInStockNumberInput = function(inStockNumber) {
        this.inStockNumberInput.sendKeys(inStockNumber);
    }

    getInStockNumberInput = function() {
        return this.inStockNumberInput.getAttribute('value');
    }

    setBookImageInput = function(bookImage) {
        this.bookImageInput.sendKeys(bookImage);
    }

    getBookImageInput = function() {
        return this.bookImageInput.getAttribute('value');
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

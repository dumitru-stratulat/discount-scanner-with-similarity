
//Set up Chai library 
let chai = require('chai');
let should = chai.should();
let assert = chai.assert;
let expect = chai.expect;
const { JSDOM } = require('jsdom');
chai.use(require('chai-dom'));
require('jsdom-global')();

//Set up Chai for testing web service
let chaiHttp = require('chai-http');
chai.use(chaiHttp);

describe('Web Content', () => {

    describe('Testing front end', () => {
        it('get searchResultPage.html file and upload for testing', (done) => {
            JSDOM.fromFile('../pages/searchResultPage.html')
                .then((dom) => {
                    global.document = dom.window.document
                })
            done();
        });
    });
    describe("Card", () => {
        it("Check if button can be clicked", () => {
            JSDOM.fromFile('../pages/searchResultPage.html')
                .then((dom) => {
                    global.document = dom.window.document
                    expect(function () { document.getElementById("searchButton").click() }).to.not.throw();
                    done();
                })
        })
        it("Check if image appear on website", () => {
            JSDOM.fromFile('../pages/searchResultPage.html')
                .then((dom) => {
                    global.document = dom.window.document
                    element = document.getElementsByClassName("productImg")[0].getAttribute('alt')
                    expect(element).to.be.equal("productImg");
                    done();
                })
        })
        it("Check if product description appeared on website", () => {
            JSDOM.fromFile('../pages/searchResultPage.html')
                .then((dom) => {
                    global.document = dom.window.document
                    element = document.getElementsByClassName("productDescription")[0].textContent
                    expect(element).to.not.be.empty;
                    done();
                })
        })
    })
});
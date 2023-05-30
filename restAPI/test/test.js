
//Server code that we are testing
let server = require ('../server')

//Set up Chai library 
let chai = require('chai');
let should = chai.should();

//Set up Chai for testing web service
let chaiHttp = require ('chai-http');
chai.use(chaiHttp);

describe('Web Service', () => {

    //Test of GET request sent to /customers
    describe('/GET specific product', () => {
        it('should GET specific product', (done) => {
            chai.request(server)
                .get('/products/3572')
                .end((err, response) => {
                    //Check the status code
                    response.should.have.status(200);

                    // //Convert returned JSON to JavaScript object
                    let resObj = JSON.parse(response.text);
``
                        resObj.should.have.property('description');
                        resObj.should.have.property('id');
                        resObj.should.have.property('img_url');
                        resObj.should.have.property('url');
                    //End test
                    done();
                });
        });
    });
      //Test of GET request sent to /customers
      describe('/GET search product', () => {
        it('should GET list of products', (done) => {
            chai.request(server)
                .get('/search?searchString=jacket')
                .end((err, response) => {
                    //Check the status code
                    response.should.have.status(200);

                    // //Convert returned JSON to JavaScript object
                    let resObj = JSON.parse(response.text);
    
                    // //Check that an array of customers is returned
                    resObj.should.be.a('array');

                    // //Check that appropriate properties are returned
                    if(resObj.length >= 1){
                        resObj[0].should.have.property('description');
                        resObj[0].should.have.property('id');
                        resObj[0].should.have.property('img_url');
                        resObj[0].should.have.property('url');
                    }
                    // End test
                    done();
                });
        });
    });
});
//Import the express and url modules
const express = require('express');
const url = require('url');
var bodyParser = require('body-parser');
//Status codes defined in external file
// require('./http_status.js');
const db = require('./database.js');
var cors = require('cors');
//The express module is a function. When it is executed it returns an app object
const app = express();
const {PORT = 8080} = process.env;
app.use(cors());
app.use(
	bodyParser.urlencoded({
		extended: true
	})
);
app.use(bodyParser.json());
//Serve up everything in public
app.use(express.static('public'));

app.use(function(req, res, next) {
	res.header('Access-Control-Allow-Origin', '*');
	res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept, authorization');
	res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, DELETE');
	next();
});
//GET search request
app.get('/search*', (request, response) => {
	searchString = request.query.searchString;
	//Run search and then send back results
	db
		.getSearch(searchString)
		.then((searchResults) => {
			response.json(searchResults);
		})
		.catch((error) => console.log(error.message));
});
app.post('/recommendations', async (request, response) => {
	const wishListProducts = request.body.products;
	let productsArray = [];
	let itemsIdArray = [];

	for (const element of wishListProducts) {
		const products = await db.getRecommendations(element.id);
		// productsArray = productsArray.concat(products);
		for (const product of products) {
			if(product.text1_product_id != element.id )
			itemsIdArray.push(product.text1_product_id)
			if(product.text2_product_id != element.id )
			itemsIdArray.push(product.text2_product_id)
		}
	}

	for (const id of itemsIdArray) {
		const [product] = await db.getProductById(id);
		productsArray.push(product)
	}
	
	let arrUniq = [...new Map(productsArray.map(v => [v.id, v])).values()]
	for (const element of wishListProducts) {
		arrUniq =  arrUniq.filter(product => product.id != element.id)
	}
	response.json(arrUniq);
});
app.post('/category', async (request, response) => {
	// console.log('recommendations');

	const category = request.body.category;
	const page = request.body.page;
	const products = await db.getProductsFromCategory(category,page);
		// productsArray = productsArray.concat(products);
		response.json(products);
});

//GET request for product by ID
//If the last part of the path is a valid product id, return data about that product
app.get('/products/*', (request, response) => {
	//Get the last part of the path
	const pathEnd = getPathEnd(request.url);
	console.log('executed' + pathEnd);
	//run search of specific product
	db
		.getProduct(pathEnd)
		.then((product) => {
			let productObj = product[0];
			response.json(productObj);
		})
		.catch((error) => console.log(error.message));
});
// processSimilarity();
async function processSimilarity() {
	let category = "shirt"
	//get specific category
	const dbData = await db.getAllProductsFromCategory(category);

	let analysedDataArray = [];

	for (let index = 0; index < dbData.length; index++) {
		const currentElement = dbData[index];

		for (let index2 = index + 1; index2 < dbData.length; index2++) {
			const nextElement = dbData[index2];
			const similarity = await apiHandler(currentElement.description, nextElement.description);

			const analysedData = {
				text1: currentElement.description,

				text1_id: currentElement.id,
				text2: nextElement.description,
				text2_id: nextElement.id,
				similarity
			};
			// console.log(analysedData)
			db.addAnalysedProducts(analysedData);
			// analysedDataArray.push(analysedData)
		}
	}
}
async function apiHandler(text1, text2) {
	const axios = require('axios');

	const options = {
		method: 'GET',
		url: 'https://twinword-text-similarity-v1.p.rapidapi.com/similarity/',
		params: {
			text1,
			text2
		},
		headers: {
			'X-RapidAPI-Key': '',
			'X-RapidAPI-Host': 'twinword-text-similarity-v1.p.rapidapi.com'
		}
	};
	const response = (await axios(options)).data.similarity;
	return response;
}
// processSimilarity();
// apiHandler();
//Start the app listening on port 8080
app.listen(PORT);
console.log('Server listening on port 8080');

//Returns the last part of the path
function getPathEnd(urlStr) {
	//Parse the URL
	let urlObj = url.parse(urlStr, true);

	//Split the path of the request into its components
	let pathArray = urlObj.pathname.split('/');

	//Return the last part of the path
	return pathArray[pathArray.length - 1];
}

//Export server for testing
module.exports = app;

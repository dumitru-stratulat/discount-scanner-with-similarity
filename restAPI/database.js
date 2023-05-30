//Import the mysql module
const mysql = require('mysql');

//Create a connection object with the user details
const connectionPool = mysql.createPool({
	connectionLimit: 1,
	host: 'scraper.cb7cj51mfxs6.us-east-1.rds.amazonaws.com',
	user: 'admin',
	password: '',
	database: 'scraper',
	debug: false
});

/** Searches for products */
module.exports.getSearch = async (searchString) => {
	let sql =
		"SELECT products.id, product_description.description,products.img_url,products.price,products.url FROM products INNER JOIN product_description ON products.title=product_description.id WHERE description LIKE '%" +
		searchString +
		"%' ";

	//Return promise to run query
	return executeQuery(sql);
};

/** Returns a promise to get details about a single product */
module.exports.getProduct = (prodId) => {
	const sql =
		'SELECT products.id, product_description.description,products.img_url,products.price,products.url FROM products INNER JOIN product_description ON products.title=product_description.id WHERE products.id=' +
		prodId;
	return executeQuery(sql);
};
module.exports.getProductsFromCategory = (category,page) => {
	let offset = (20*page)-1;
	const sql =
		'SELECT products.id, product_description.description,products.img_url,products.price,products.url FROM products INNER JOIN product_description ON products.title=product_description.id WHERE products.product_category ="'+category+'" LIMIT 30 OFFSET '+offset+'';
		return executeQuery(sql);
};
module.exports.getAllProductsFromCategory = (category) => {
	const sql =
		'SELECT products.id, product_description.description,products.img_url,products.price,products.url FROM products INNER JOIN product_description ON products.title=product_description.id WHERE products.product_category ="'+category+'"';
		return executeQuery(sql);
};
module.exports.addAnalysedProducts = (element) => {

		const sql = 'INSERT INTO product_analysis (text1_product_id, text2_product_id, similarity) VALUES ('+element.text1_id+', '+element.text2_id+', '+element.similarity+');'
		executeQuery(sql);
};
module.exports.getProductById = (id) => {
	const sql = 'SELECT  products.id ,products.price,products.url ,products.img_url,product_description.description from products JOIN product_description  on products.title = product_description.id WHERE products.id = '+id;
	return executeQuery(sql);
};
module.exports.getRecommendations = (id) => {
	const sql = 'SELECT product_description.description, products.id,products.img_url, text1_product_id, text2_product_id,similarity from product_analysis JOIN products ON  product_analysis.text2_product_id = products.id  JOIN  product_description ON products.title=product_description.id WHERE text1_product_id = '+id+' or text2_product_id = '+id+' ORDER BY similarity DESC LIMIT 5'
	return executeQuery(sql);
};
/** Wraps connection pool query in a promise and returns the promise */
async function executeQuery(sql) {
	//Wrap query around promise
	let queryPromise = new Promise((resolve, reject) => {
		//Execute the query
		connectionPool.query(sql, function(err, result) {
			//Check for errors
			if (err) {
				//Reject promise if there are errors
				reject(err);
			}
			//Resole promise with data from database.
			resolve(result);
		});
	});

	//Return promise
	return queryPromise;
}

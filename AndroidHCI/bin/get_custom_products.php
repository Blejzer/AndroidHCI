<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();

if (isset($_GET['regPlate'])) {
$terms = $_GET['regPlate'];
}
// $regPlate = mysql_real_escape_string($regPlate);
// $regPlate = addcslashes($regPlate, "%_");

// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get filtered vehicles from products table
// $sql = sprintf("SELECT * FROM `products` WHERE `name` LIKE '".$regPlate."%' ORDER BY created_at DESC");
// $result = mysql_query($sql) or die(mysql_error());
	
	function search_split_terms($terms){

		$terms = preg_replace("/\"(.*?)\"/e", "search_transform_term('\$1')", $terms);
		$terms = preg_split("/\s+|,/", $terms);

		$out = array();

		foreach($terms as $term){

			$term = preg_replace("/\{WHITESPACE-([0-9]+)\}/e", "chr(\$1)", $term);
			$term = preg_replace("/\{COMMA\}/", ",", $term);

			$out[] = $term;
		}

		return $out;
	}

	function search_transform_term($term){
		$term = preg_replace("/(\s)/e", "'{WHITESPACE-'.ord('\$1').'}'", $term);
		$term = preg_replace("/,/", "{COMMA}", $term);
		return $term;
	}
	
	function search_escape_rlike($string){
		return preg_replace("/([.\[\]*^\$])/", '\\\$1', $string);
	}
	
	function search_db_escape_terms($terms){
		$out = array();
		foreach($terms as $term){
			$out[] = '[[:<:]]'.AddSlashes(search_escape_rlike($term)).'[[:>:]]';
		}
		return $out;
	}
	
	$terms = search_split_terms($terms);
	$terms_db = search_db_escape_terms($terms);

	$parts = array();
	foreach($terms_db as $term_db){
		$parts[] = "name RLIKE '$term_db'";
	}
	$parts = implode(' OR ', $parts);

	$sql = "SELECT * FROM products WHERE $parts";

	$result = mysql_query($sql) or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["pid"] = $row["pid"];
        $product["name"] = $row["name"];
		$product["description"] = $row["description"];
        $product["created_at"] = $row["created_at"];
        $product["updated_at"] = $row["updated_at"];
		$product["link"] = $row["link"];
 
        // push single product into final response array
        array_push($response["products"], $product);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
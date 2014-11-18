<?php
  
    // array for JSON response
    $response = array();
    $file_path = "images/";

    // check for required fields
   if (isset($_POST['link'])) {
 
    $link = $_POST['link'];

    $result = unlink($file_path.$link);

    if($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Image successfully deleted.";

        // echoing JSON response
        echo json_encode($response);
    } else{
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
 ?>
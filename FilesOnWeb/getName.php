<?php
require_once 'Include/DB_Functions.php';
$db = new DB_Functions();

//json response array
$response = array("error" => FALSE);
if (isset($_POST['uid'])) {
    $uid = $_POST['uid'];
    $user = $db->getUserByUID($uid);
    if ($user != false) {
      $response["error"] = FALSE;
      $response["name"] = $user["name"];
      echo json_encode($response);
    } else {
      $response["error"] = TRUE;
      $response["error_msg"] = "Database not found!";
      echo json_encode($response);
    }
  } else {
      $response["error"] = TRUE;
      $response["error_msg"] = "Required parameters is missing!";
      echo json_encode($response);
  }

?>

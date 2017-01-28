<?php

require_once 'Include/DB_Functions.php';
$db = new DB_Functions();

//json response array
$response = array("error" => FALSE);

if (isset($_POST['uid']) && isset($_POST['post'])){
  //reciving the post params
  $uid = $_POST['uid'];
  $post = $_POST['post'];
  $stat = $_POST['stat']

  $posting = $db->storePost($uid, $post);
  if ($posting) {
    $response["error"] = FALSE;
    echo json_encode($response);
  } else {
    //post failed to store
    $response["error"] = TRUE;
    $response["error_msg"] = "Unknown error occurred in process!";
    echo json_encode($response);
  }
} elseif (isset($_POST['stat'])) {
  $posting = $db->getPost();
  $response["error"] = FALSE;
  $response["posting"] = $posting;
  echo json_encode($response);
} else {
  $response["error"] = TRUE;
  $response["error_msg"] = "Required parameters is missing!";
  echo json_encode($response);
}

?>

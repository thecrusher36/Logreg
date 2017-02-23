<?php

	require_once 'Include/Config.php';
	require_once 'Include/DB_Functions.php';

	$db = new DB_Functions();
	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
	$sql = "SELECT * FROM posting";
	$r = mysqli_query($conn,$sql);

  $result = array();

  while ($row = mysqli_fetch_array($r)) {
    array_push($result,array(
      'id'=>$row['id'],
			'name'=>$db->getUserByUID($row['user_unique_id']),
      'post'=>$row['post'],
      'created_at'=>$row['created_at'],
      'approved'=>$row['approved']
    ));
  }
  echo json_encode(array('result'=>$result));

	mysqli_close($conn);
?>

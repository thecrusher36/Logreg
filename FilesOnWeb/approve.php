<?php

	require_once 'Include/Config.php';
	require_once 'Include/DB_Functions.php';

	$db = new DB_Functions();
	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
	// $sql = "SELECT * FROM posting";
	// $r = mysqli_query($conn,$sql);

  $id = $_POST['id'];
  $sql = "SELECT approved FROM posting WHERE id = $id";
  $r = mysqli_query($conn,$sql);
  $row = mysqli_fetch_row($r);
  if ($row[0] == "0"){
    mysqli_query($conn, "UPDATE posting SET approved = '1' WHERE id = $id");
  } else {
    mysqli_query($conn, "UPDATE posting SET approved = '0' WHERE id = $id");
  }
  mysqli_free_result($r);
?>

<?php
	private $conn;

	//constructor
	function __construct() {
		require_once '/Include/DB_Connect.php';
		require_once '/Include/Config.php';
		//connecting to database
		$db = new DB_Connect();
		$this->conn = $db->connect();
	}

	//destructor
	function __destructor() {

	}

  $stmt = $this->conn->prepare("SELECT * FROM posting");
  $stmt->execute();
  $r = $stmt->get_result();
  $result = array();

  while ($row = mysql_fetch_array($r)) {
    array_push($result,array(
      'id'=>$row['id'],
      'uid'=>$row['user_unique_id'],
      'post'=>$row['post'],
      'created_at'=>$row['created_at'],
      'approved'=>$row['approved']
    ));
  }
  echo json_encode(array('result'=>$result));

  $stmt->close();
?>

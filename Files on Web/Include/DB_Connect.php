<?php
class DB_Connect {
	private $conn;
	
	//connecting to database
	public function connect() {
		require_once 'include/Config.php';
		
		//connecting to mysql databse
		$this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
	
		//return database handler
		return $this->conn;
	}
}
?>
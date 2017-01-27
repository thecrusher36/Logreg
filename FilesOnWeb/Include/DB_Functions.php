<?php
class DB_Functios {
	private $conn;
	
	//constructor
	function __construct() {
		require_once 'DB_Connect.php';
		//connecting to database
		$db = new DB_Connect();
		$this->conn = $db->connect();
	}
	
	//destructor
	function __destructor() {
		
	}
	
	//Storing new user
	//return user detail
	public function storeUser($name, $email, $password) {
		$uuid = uniqid('', true);
		$hash = $this->hashSSHA($password);
		$encrypted_password = $hash["encrypted"]; //encrypted password
		$salt = $hash["salt"]; //salt
		
		$stmt = $this->conn->prepare("INSERT INTO users(unique_id, name, email, encrypted_password, salt, created_at) VALUES(?, ?, ?, ?, ?, NOW())");
		$stmt->bind_param("sssss", $uuid, $name, $email, $encrypted_password, $salt);
		$result = $stmt->execute();
		$stmt->close();
		
		//check for successful store
		if ($result) {
			$stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
			$stmt->bind_param("s", $email);
			$stmt->execute();
			$user = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			
			return $user;
		} else {
			return false;
		}
	}
	
	//
	//Get user by email and password
	//
	public function getUserByEmailAndPassword($email, $password) {
		
		$stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
		
		$stmt->bind_param("s", $email);
		
		if ($stmt->execute()) {
			$user = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			
			//verifying user password
			$salt = $user['salt'];
			$encrypted_password = $user['encrypted_password'];
			$hash = $this->checkhashSSHA($salt, $password);
			//ceck for password equality
			if ($encrypted_password == $hash) {
				//user authentication details are correct
				return $user;
			}
		} else {
			return NULL;
		}
	}

	//
	//Check user is exixted or not
	//
	public function isUserExisted($email) {
		$stmt = $this->conn->prepare("SELECT email from users WHERE email = ?");
		
		$stmt->bind_param("s", $email);
		
		$stmt->execute();
		
		$stmt->store_result();
		
		if ($stmt->num_rows > 0) {
			//user existed
			$stmt->close();
			return true;
		} else {
			//user not existed
			$stmt->close();
			return false;
		}
	}

	//encrypting password
	//@param password
	//return salt and encrypted password
	public function hashSSHA($password) {
		
		$salt = sha1(rand());
		$salt = substr($salt, 0, 10);
		$encrypted = base64_encode(sha1($password . $salt, true) . $salt);
		$hash = array("salt" => $salt, "encrypted" => $encrypted);
		return $hash;
	}

	
	//decrypting password
	//@param salt, password
	//return hash string
	
	public function checkhashSSHA($salt, $password) {
		
		$hash = base64_encode(sha1($password . $salt, true) . $salt);
		
		return $hash;
	}
	
}

?>
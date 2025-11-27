<?php
$servername = "localhost";
$username = "root"; // your MySQL username
$password = ""; // your MySQL password
$dbname = "supermaze";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$user = $_POST['username'];
$mode = $_POST['mode'];
$time = $_POST['time_taken'];

$stmt = $conn->prepare("INSERT INTO victories (username, mode, time_taken) VALUES (?, ?, ?)");
$stmt->bind_param("ssd", $user, $mode, $time);
$stmt->execute();

echo "Saved";

$stmt->close();
$conn->close();
?>

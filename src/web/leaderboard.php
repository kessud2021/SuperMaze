<?php
$conn = new mysqli("localhost", "root", "", "supermaze");
$result = $conn->query("SELECT username, mode, time_taken, completed_at FROM victories ORDER BY time_taken ASC LIMIT 20");

echo "<table border='1' cellspacing='0' cellpadding='5'>";
echo "<tr><th>Username</th><th>Mode</th><th>Time</th><th>Date</th></tr>";
while($row = $result->fetch_assoc()) {
  echo "<tr><td>{$row['username']}</td><td>{$row['mode']}</td><td>{$row['time_taken']}s</td><td>{$row['completed_at']}</td></tr>";
}
echo "</table>";

$conn->close();
?>

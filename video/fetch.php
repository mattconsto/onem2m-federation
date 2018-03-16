<?php

$url = 'https://portal.sensivision.co/~/in-cse-3/onem2m/sensor_data/camera/latest';

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
$output = curl_exec($ch);
curl_close ($ch);

echo json_decode(base64_decode(json_decode($output, true)["m2m:cin"]["con"]), true)["value"];

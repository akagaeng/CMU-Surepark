//http request (POST)
// this method makes a HTTP connection to the server:
void httpRequest(String _PostData) {
  // close any connection before send a new request.
  // This will free the socket on the WiFi shield
  httpClient.stop();

  // if there's a successful connection:
  if (httpClient.connect(server, WEB_SERVER_PORT)) {
    Serial.println("connecting...");
    // send the HTTP POST request:
   httpClient.println("POST /api/checkparkingslot HTTP/1.1");
   httpClient.println("Host: 192.168.1.180");
   httpClient.println("User-Agent: ArduinoWiFi/1.1");
   httpClient.println("Connection: close");
   httpClient.println("Content-Type: application/x-www-form-urlencoded;");
   httpClient.print("Content-Length: ");
   httpClient.println(_PostData.length());
   httpClient.println();
   httpClient.println(_PostData);
   httpClient.println();

   Serial.println(_PostData);

   delay(2000); //딜레이있어야 씹히지않고 전송됨
    /*
    // note the time that the connection was made:
    lastConnectionTime = millis();
  } else {
    // if you couldn't make a connection:
    Serial.println("connection failed");

      */
  }

}

String httpResponse(){
  String receivedMsg="";
  while (httpClient.available()) {
    char c = httpClient.read();
    receivedMsg += (String)c;
    //Serial.print(c);
  }//endwhile
  return receivedMsg;
}


//wifi상태정보
void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
  Serial.println("---------------------------------------------------------------");
}

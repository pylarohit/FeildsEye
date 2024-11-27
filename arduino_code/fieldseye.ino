#include <WiFi.h>
#include <WebServer.h>

const char* ssid = "DDS";
const char* password = "ninet.com";
WebServer server(80);

const int moistureSensorPin = 34;
const int relayPin = 2;
const int buzzerRelayPin = 4;

int getSoilMoisture() {
  int moistureValue = analogRead(moistureSensorPin);
  return map(moistureValue, 0, 4095, 0, 100);
}

void handleRoot() {
  int soilMoisture = getSoilMoisture();
  String jsonResponse = "{ \"soilMoisture\": " + String(soilMoisture) + " }";
  server.send(200, "application/json", jsonResponse);
}

void handleRelayControl() {
  if (server.hasArg("state")) {
    String state = server.arg("state");
    if (state == "on") {
      digitalWrite(relayPin, LOW);
      server.send(200, "text/plain", "Water Pump Relay turned ON");
      Serial.println("Water Pump Relay turned ON");
    } else if (state == "off") {
      digitalWrite(relayPin, HIGH);
      server.send(200, "text/plain", "Water Pump Relay turned OFF");
      Serial.println("Water Pump Relay turned OFF");
    } else {
      server.send(400, "text/plain", "Invalid state");
    }
  } else {
    server.send(400, "text/plain", "Missing 'state' argument");
  }
}

void handleBuzzerControl() {
  if (server.hasArg("state")) {
    String state = server.arg("state");
    if (state == "on") {
      digitalWrite(buzzerRelayPin, LOW);
      server.send(200, "text/plain", "Buzzer Relay turned ON");
      Serial.println("Buzzer Relay turned ON");
    } else if (state == "off") {
      digitalWrite(buzzerRelayPin, HIGH);
      server.send(200, "text/plain", "Buzzer Relay turned OFF");
      Serial.println("Buzzer Relay turned OFF");
    } else {
      server.send(400, "text/plain", "Invalid state");
    }
  } else {
    server.send(400, "text/plain", "Missing 'state' argument");
  }
}

void setup() {
  Serial.begin(115200);
  
  pinMode(moistureSensorPin, INPUT);
  pinMode(relayPin, OUTPUT);
  pinMode(buzzerRelayPin, OUTPUT);
  digitalWrite(relayPin, HIGH);
  digitalWrite(buzzerRelayPin, HIGH);

  WiFi.begin(ssid, password);
  Serial.println("Connecting to Wi-Fi...");
  int retryCount = 0;
  while (WiFi.status() != WL_CONNECTED && retryCount < 20) {
    delay(1000);
    Serial.print(".");
    retryCount++;
  }
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("\nWi-Fi connected");
    Serial.print("IP Address: ");
    Serial.println(WiFi.localIP());
  } else {
    Serial.println("\nFailed to connect to Wi-Fi. Check credentials or network.");
    while (true);
  }

  server.on("/", handleRoot);
  server.on("/relay", handleRelayControl);
  server.on("/buzzer", handleBuzzerControl);

  server.begin();
  Serial.println("HTTP server started");
}

void loop() {
  server.handleClient();
}

long ProximityVal(int Pin)
{
    long duration = 0;
    pinMode(Pin, OUTPUT);         // Sets pin as OUTPUT
    digitalWrite(Pin, HIGH);      // Pin HIGH
    delay(1);                     // Wait for the capacitor to stabilize

    pinMode(Pin, INPUT);          // Sets pin as INPUT
    digitalWrite(Pin, LOW);       // Pin LOW
    while(digitalRead(Pin))       // Count until the pin goes
    {                             // LOW (cap discharges)
       duration++;                
    }   
    return duration;              // Returns the duration of the pulse
}

void InitEntryExitLEDs()
{
  int i;
  for (i=26; i<=29; i++)
  {
    pinMode(i, OUTPUT);
    digitalWrite(i, HIGH);
  }
}



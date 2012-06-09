package ezt.Reminder;

import org.junit.Test;

public class AePlayWaveTest {

	@Test
	public void testAePlayWaveString() {
		AePlayWave alarm= new AePlayWave("alarmSound.wav");
		alarm.start();
	}

}

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
public class HighScoreTest {
	@Test
	public void testFileDelete() {
		File highScore = new File("highScore");
		highScore.delete();
		LevelGenerator.newRecord(1, 0);
		highScore = new File("highScore");
		assertTrue(highScore.exists());
	}
}

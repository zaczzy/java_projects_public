
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		int [][] level = new int[8][50];
		LevelGenerator.generateLevel();	
		level = LevelGenerator.readLevel();
		for(int i = 0; i< 8; i++){
			for (int j = 0; j < 50; j++){
				System.out.print(level[i][j] + " ");
			}
			System.out.println();
		}
		
   */   int i = 0;
		while(i < 100){
		System.out.println((int)(Math.random() * 3 + 1));
		i++;
		}
	}
}

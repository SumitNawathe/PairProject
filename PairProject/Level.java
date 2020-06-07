public abstract class Level {
	private int progressState;
	public int getProgressState () { return progressState; }
	public void incrementProgressState () { progressState++; }
	public abstract void initializeGame (Game game);
	public abstract boolean update (Game game); // true if game has been won
}

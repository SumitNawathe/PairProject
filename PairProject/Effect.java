public abstract class Effect extends Mesh {
	private Vector pos;
	public Vector getPos () { return pos; }
	public void setPos (Vector pos) { this.pos = pos; }
	
	public abstract void update (Game game);
}

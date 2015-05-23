package adapter;

public interface Queue {
	public boolean isEmpty();
	public int getSize();
	public void offer(int element);
	public int peek();
	public int poll();
}

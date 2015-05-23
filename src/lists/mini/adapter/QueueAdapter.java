package adapter;

import ringpuffer.Ringpuffer;

public class QueueAdapter implements Queue {

	private Ringpuffer rp;
	
	public QueueAdapter(int capacity) {
		rp = new Ringpuffer(capacity);
	}

	@Override
	public boolean isEmpty() {
		return rp.isEmpty();
	}

	@Override
	public int getSize() {
		return rp.getSize();
	}

	@Override
	public void offer(int element) {
		rp.offer(element);
	}

	@Override
	public int peek() {
		return rp.peek();
	}

	@Override
	public int poll() {
		return rp.poll();
	}

}
